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
import com.yupi.springbootinit.model.dto.student.StudentAddRequest;
import com.yupi.springbootinit.model.dto.student.StudentEditRequest;
import com.yupi.springbootinit.model.dto.student.StudentQueryRequest;
import com.yupi.springbootinit.model.dto.student.StudentWithClazzDTO;
import com.yupi.springbootinit.model.entity.Student;
import com.yupi.springbootinit.service.StudentService;
import com.yupi.springbootinit.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 学生接口
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Resource
    private StudentService studentService;

    /**
     * 创建学生
     */
    @PostMapping("/add")
    public BaseResponse<Long> addStudent(@RequestBody StudentAddRequest studentAddRequest) {
        if(studentAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求失败");
        }
        Student student = new Student();
        BeanUtils.copyProperties(studentAddRequest,student);
        boolean result = studentService.save(student);
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(student.getStudentId());
    }

    /**
     * 删除学生
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteStudent(@RequestBody DeleteRequest deleteRequest) {
        if(deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求失败");
        }
        Long id = deleteRequest.getId();
        Student student = studentService.getById(id);
        ThrowUtils.throwIf(student == null,ErrorCode.NOT_FOUND_ERROR);
        boolean b = studentService.removeById(deleteRequest);
        return ResultUtils.success(b);
    }

    /**
     * 更新学生
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateStudent(@RequestBody StudentEditRequest studentEditRequest) {
        //校验
        if(studentEditRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求失败");
        }
        //转换参数类型
        Student student = new Student();
        BeanUtils.copyProperties(studentEditRequest,student);
        //进行更新
        boolean result = studentService.updateById(student);
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);
        //返回结果
        return ResultUtils.success(result);
    }

    /**
     * 查询学生列表
     */
//    @PostMapping("/list")
//    public BaseResponse<Page<Student>> listStudents(@RequestBody StudentQueryRequest studentQueryRequest) {
//        if(studentQueryRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
//        }
//        long current = studentQueryRequest.getCurrent();
//        long pageSize = studentQueryRequest.getPageSize();
//        ThrowUtils.throwIf(current <= 0 || pageSize <= 0 || pageSize > 20,ErrorCode.PARAMS_ERROR);
//        Page<Student> page = studentService.page(new Page<>(current, pageSize),
//                getQueryWrapper(studentQueryRequest));
//        return ResultUtils.success(page);
//    }
    @PostMapping("/list")
    public BaseResponse<Page<StudentWithClazzDTO>> listStudents(
            @RequestBody @Valid StudentQueryRequest request) {

        // 参数校验
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 分页参数校验（建议单独封装校验方法）
        if (request.getCurrent()  <= 0 || request.getPageSize()  <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分页参数错误");
        }

        // 调用服务层
        Page<StudentWithClazzDTO> result = studentService.queryWithClazz(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取包装类
     */
    private QueryWrapper<Student> getQueryWrapper(StudentQueryRequest studentQueryRequest) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if(studentQueryRequest == null) {
            return queryWrapper;
        }
        String sortField = studentQueryRequest.getSortField();
        String sortOrder = studentQueryRequest.getSortOrder();
        Long id = studentQueryRequest.getId();
        String name = studentQueryRequest.getName();
        String clazzName = studentQueryRequest.getClazzName();

        queryWrapper.eq(id != null && id > 0, "studentId", id);
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.like(StringUtils.isNotBlank(clazzName), "clazzName", clazzName);

        queryWrapper.orderBy(
                SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField
        );


        //返回指定字段
        queryWrapper.select( "studentId", "name", "gender", "homeworkStation", "studyStation");
        return queryWrapper;
    }


}
