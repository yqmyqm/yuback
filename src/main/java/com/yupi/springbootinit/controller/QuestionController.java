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
import com.yupi.springbootinit.model.dto.prescription.PrescriptionQueryRequest;
import com.yupi.springbootinit.model.dto.question.QuestionAddRequest;
import com.yupi.springbootinit.model.dto.question.QuestionEditRequest;
import com.yupi.springbootinit.model.dto.question.QuestionQueryRequest;
import com.yupi.springbootinit.model.entity.Prescription;
import com.yupi.springbootinit.model.entity.Question;
import com.yupi.springbootinit.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 题目接口
 */
@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {

    @Resource
    public QuestionService questionService;

    /**
     * 增加题目
     */
    @RequestMapping("/add")
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequetion) {
        if(questionAddRequetion == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequetion,question);
        Boolean result = questionService.save(question);
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(question.getId());
    }

    /**
     * 删除题目
     */
    @RequestMapping("/delete")
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest request) {
        if(request == null || request.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(request.getId());
        ThrowUtils.throwIf(question == null,ErrorCode.NOT_FOUND_ERROR);
        boolean result = questionService.removeById(request.getId());
        return ResultUtils.success(result);
    }

    /**
     * 修改题目
     */
    @RequestMapping("/edit")
    public BaseResponse<Boolean> editQuestion(@RequestBody QuestionEditRequest questionEditRequest) {
        if(questionEditRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionEditRequest,question);
        boolean result = questionService.updateById(question);
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 获取题目
     */
    @RequestMapping("/list")
    public BaseResponse<Page<Question>> listQuestion(@RequestBody QuestionQueryRequest questionQueryRequest) {
        if(questionQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        ThrowUtils.throwIf(current < 0 || size < 0 || size > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                getQueryWrapper(questionQueryRequest));
        return ResultUtils.success(questionPage);
    }

    private QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest) {

        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if(questionQueryRequest == null) {
            return queryWrapper;
        }

        Long id = questionQueryRequest.getId();
        String title = questionQueryRequest.getTitle();
        String type = questionQueryRequest.getType();
        String sortField = questionQueryRequest.getSortField();
        String sortOrder = questionQueryRequest.getSortOrder();

        queryWrapper.eq(id != null && id > 0 , "id", id);
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(type), "type", type);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }



}
