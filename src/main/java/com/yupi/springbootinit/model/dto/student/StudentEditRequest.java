package com.yupi.springbootinit.model.dto.student;


import lombok.Data;

import java.io.Serializable;

/**
 * 发生更新学生信息请求
 */
@Data
public class StudentEditRequest implements Serializable {

    /**
     * 学生id
     */
    private Long studentId;

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
