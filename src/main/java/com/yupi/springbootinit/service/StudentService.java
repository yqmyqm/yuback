package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.student.StudentQueryRequest;
import com.yupi.springbootinit.model.dto.student.StudentWithClazzDTO;
import com.yupi.springbootinit.model.entity.Student ;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 虞骐铭
* @description 针对表【student】的数据库操作Service
* @createDate 2025-03-22 18:40:17
*/
public interface StudentService extends IService<Student> {


    Page<StudentWithClazzDTO> queryWithClazz(StudentQueryRequest request);

//    List<StudentQueryRequest> pageStudents(StudentQueryRequest studentQueryRequest);


}
