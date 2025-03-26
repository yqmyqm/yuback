package com.yupi.springbootinit.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.model.dto.student.StudentQueryRequest;
import com.yupi.springbootinit.model.dto.student.StudentWithClazzDTO;
import com.yupi.springbootinit.model.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

import javax.annotation.Nullable;
import java.util.List;

/**
* @author 虞骐铭
* @description 针对表【student】的数据库操作Mapper
* @createDate 2025-03-22 18:40:17
* @Entity com.yupi.springbootinit.model.entity.Student
*/
public interface StudentMapper extends BaseMapper<Student> {
    // 添加分页参数支持
//    List<StudentQueryRequest> selectStudentsWithClazzName(
//            @Param("keyword") @Nullable String keyword
//    );
    Page<StudentWithClazzDTO> selectStudentsWithClazzName(
            @Param("pageSize") IPage<?> page,
            @Param("keyword") @Nullable String keyword
    );
}




