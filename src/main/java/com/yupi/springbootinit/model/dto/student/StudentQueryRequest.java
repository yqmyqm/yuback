package com.yupi.springbootinit.model.dto.student;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 前端的查询请求
 */
@Data
public class StudentQueryRequest extends PageRequest implements Serializable {

    /**
     * 学生id
     */
    private Long id;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 学生班级名
     */
    private String clazzName;

    /**
     * 模糊关键词
     */
    private String keyword;      // 模糊搜索关键词

    private static final long serialVersionUID = 1L;
}
