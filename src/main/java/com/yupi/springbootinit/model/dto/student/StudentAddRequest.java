package com.yupi.springbootinit.model.dto.student;

import lombok.Data;

import java.io.Serializable;

/**
 * 发送创建学生请求
 */
@Data
public class StudentAddRequest implements Serializable {

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 班级id
     */
    private Long clazzId;

    private static final long serialVersionUID = 1L;
}
