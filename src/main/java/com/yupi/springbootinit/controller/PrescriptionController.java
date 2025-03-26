package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.prescription.PrescriptionAddRequest;
import com.yupi.springbootinit.model.dto.prescription.PrescriptionEditRequest;
import com.yupi.springbootinit.model.dto.prescription.PrescriptionQueryRequest;
import com.yupi.springbootinit.model.entity.Prescription;
import com.yupi.springbootinit.service.PrescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 方剂管理
 */
@RestController
@RequestMapping("/prescription")
@Slf4j
public class PrescriptionController {

    @Resource
    PrescriptionService prescriptionService;

    /**
     * 添加方剂
     * @param prescriptionAddRequest
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addPrescription(@RequestBody PrescriptionAddRequest prescriptionAddRequest) {
        if(prescriptionAddRequest == null)
            return null;
        Prescription prescription = new Prescription();
        BeanUtils.copyProperties(prescriptionAddRequest, prescription);
        boolean result = prescriptionService.save(prescription);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(prescription.getId());
    }

    /**
     * 删除方剂
     */
    @PostMapping("delete")
    public BaseResponse<Boolean> deletePrescription(@RequestBody DeleteRequest prescriptionDeleteRequest) {
        //请求是否正确
        if(prescriptionDeleteRequest == null || prescriptionDeleteRequest.getId() <= 0)
            return null;
        //请求的id
        Long id = prescriptionDeleteRequest.getId();
        //判断id是否存在
        Prescription prescription = prescriptionService.getById(id);
        ThrowUtils.throwIf(prescription == null, ErrorCode.NOT_FOUND_ERROR);
        //进行删除
        boolean result = prescriptionService.removeById(id);
        return ResultUtils.success(result);
    }

    /**
     * 修改方剂
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editPrescription(@RequestBody PrescriptionEditRequest prescriptionEditRequest) {
        if(prescriptionEditRequest == null) {
            return null;
        }
        Prescription prescription = new Prescription();
        BeanUtils.copyProperties(prescriptionEditRequest, prescription);
        boolean result = prescriptionService.updateById(prescription);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 查询方剂列表信息
     */
    @PostMapping("/list")
    public BaseResponse<Page<Prescription>> listPrescriptionByPage(@RequestBody PrescriptionQueryRequest prescriptionQueryRequest) {
        if(prescriptionQueryRequest == null) {
            return null;
        }
        //当前页数
        long current = prescriptionQueryRequest.getCurrent();
        //每页大小
        long size = prescriptionQueryRequest.getPageSize();
        ThrowUtils.throwIf( current < 0 || size < 0 || size > 20, ErrorCode.PARAMS_ERROR);
        Page<Prescription> page = prescriptionService.page(new Page<>(current, size),
                getQueryWrapper(prescriptionQueryRequest));
        return ResultUtils.success(page);
    }

    /**
     * 获取查询包装类
     */
    private QueryWrapper<Prescription> getQueryWrapper(PrescriptionQueryRequest prescriptionQueryRequest) {

        QueryWrapper<Prescription> queryWrapper = new QueryWrapper<>();
        if(prescriptionQueryRequest == null) {
            return queryWrapper;
        }

        Long id = prescriptionQueryRequest.getId();
        String name = prescriptionQueryRequest.getName();
        String source = prescriptionQueryRequest.getSource();
        String therapy = prescriptionQueryRequest.getTherapy();
        String sortField = prescriptionQueryRequest.getSortField();
        String sortOrder = prescriptionQueryRequest.getSortOrder();

        queryWrapper.eq(id != null && id > 0 , "id", id);
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.like(StringUtils.isNotBlank(source), "source", source);
        queryWrapper.like(StringUtils.isNotBlank(therapy), "therapy", therapy);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }

}
