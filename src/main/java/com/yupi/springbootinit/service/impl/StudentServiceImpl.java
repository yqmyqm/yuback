package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.dto.student.StudentQueryRequest;
import com.yupi.springbootinit.model.dto.student.StudentWithClazzDTO;
import com.yupi.springbootinit.model.entity.Student;
import com.yupi.springbootinit.service.StudentService;
import com.yupi.springbootinit.mapper.StudentMapper;
import org.springframework.stereotype.Service;

/**
* @author 虞骐铭
* @description 针对表【student】的数据库操作Service实现
* @createDate 2025-03-22 18:40:17
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

    @Override
    public Page<StudentWithClazzDTO> queryWithClazz(StudentQueryRequest request) {
        // 构建分页参数
        Page<StudentWithClazzDTO> page = new Page<>(
                request.getCurrent(),
                request.getPageSize()
        );

        // 执行多表查询
        return baseMapper.selectStudentsWithClazzName(page,  request.getKeyword());
    }
//    @Override
//    public List<StudentQueryRequest> listWithClazzName(@Nullable String keyword) {
//        // 空值处理逻辑
//        String processedKeyword = StringUtils.trimToNull(keyword);
//        return baseMapper.selectStudentsWithClazzName(processedKeyword);
//    }

//    @Override
//    // 修改分页查询方法
//    public Page<StudentQueryRequest> pageStudents(Page<StudentQueryRequest> page, String keyword) {
//        return studentMapper.selectStudentsWithClazzName(page,  keyword);
//    }
}




