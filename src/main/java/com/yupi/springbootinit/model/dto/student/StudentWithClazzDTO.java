package com.yupi.springbootinit.model.dto.student;

import lombok.Data;

/**
 * 返回给前端的数据
 */
@Data
public class StudentWithClazzDTO {

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
     * 所属班级
     */
    private String clazzName; // 来自clazz表

    /**
     * 作业情况
     */
    private String homeworkStation;

    /**
     * 学习情况
     */
    private String studyStation;
}
