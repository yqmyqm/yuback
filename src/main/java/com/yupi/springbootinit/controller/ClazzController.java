package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.clazz.ClazzAddRequest;
import com.yupi.springbootinit.model.dto.clazz.ClazzEditRequest;
import com.yupi.springbootinit.model.dto.clazz.ClazzQueryRequest;
import com.yupi.springbootinit.service.ClazzService;
import com.yupi.springbootinit.service.UserService;
import com.yupi.springbootinit.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import com.yupi.springbootinit.model.entity.Clazz;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 班级接口
 */
@RestController
@RequestMapping("/clazz")
@Slf4j
public class ClazzController {
    @Resource
    private ClazzService clazzService;

    @Resource
    private UserService userService;

    /**
     * 创建班级
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addClazz(@RequestBody ClazzAddRequest clazzAddRequest) {
        if (clazzAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Clazz clazz = new Clazz();
        BeanUtils.copyProperties(clazzAddRequest, clazz);
        boolean result = clazzService.save(clazz);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newClazzId = clazz.getClazzId();
        return ResultUtils.success(newClazzId);
    }

    /**
     * 删除班级
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteClazz(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if(deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        Clazz oldClazz = clazzService.getById(id);
        ThrowUtils.throwIf(oldClazz == null, ErrorCode.NOT_FOUND_ERROR);
//        // 仅管理员可删除
//        if (!userService.isAdmin(request)) {
//            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
//        }
        // 逻辑删除
        boolean b = clazzService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新班级
     * @param clazzEditRequest
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateClazz(@RequestBody ClazzEditRequest clazzEditRequest, HttpServletRequest request) {
        //判断参数是否合法
        if (clazzEditRequest == null || clazzEditRequest.getClazzId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断是否存在
        Long id = clazzEditRequest.getClazzId();
        Clazz oldClazz = clazzService.getById(id);
        ThrowUtils.throwIf(oldClazz == null, ErrorCode.NOT_FOUND_ERROR);
        // 将请求参数转化为实体类
        Clazz clazz = new Clazz();
        BeanUtils.copyProperties(clazzEditRequest, clazz);
        Boolean result = clazzService.updateById(clazz);
        return ResultUtils.success(result);
    }

    /**
     * 根据id获取对象
     * @param id
    */
    @GetMapping("/get")
    public BaseResponse<Clazz> getClazzById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Clazz clazz = clazzService.getById(id);
        if(clazz == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(clazz);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param clazzQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list")
    public BaseResponse<Page<Clazz>> listClazzByPage(@RequestBody ClazzQueryRequest clazzQueryRequest,
                                                     HttpServletRequest request) {
        if(clazzQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = clazzQueryRequest.getCurrent();
        long size = clazzQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf( current < 0 || size < 0 || size > 20, ErrorCode.PARAMS_ERROR);
        Page<Clazz> clazzPage = clazzService.page(new Page<>(current, size),
                getQueryWrapper(clazzQueryRequest));
        return ResultUtils.success(clazzPage);
    }

    /**
     * 获取查询包装类
     */
    private QueryWrapper<Clazz> getQueryWrapper(ClazzQueryRequest clazzQueryRequest) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        if (clazzQueryRequest == null) {
            return queryWrapper;
        }

        Long id = clazzQueryRequest.getId();
        String clazzName = clazzQueryRequest.getClazzName();
        String grade = clazzQueryRequest.getGrade();
        String field = clazzQueryRequest.getField();
        Long clazzSize = clazzQueryRequest.getClazzSize();
        String sortField= clazzQueryRequest.getSortField();
        String sortOrder = clazzQueryRequest.getSortOrder();

        queryWrapper.eq(id != null && id > 0, "id", id);
        queryWrapper.like(StringUtils.isNotBlank(clazzName), "clazzName", clazzName);
        queryWrapper.like(StringUtils.isNotBlank(grade), "grade", grade);
        queryWrapper.like(StringUtils.isNotBlank(field), "field", field);
        queryWrapper.like(clazzSize != null && clazzSize > 0, "clazzSize", clazzSize);
        queryWrapper.orderBy(
                SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }
}
