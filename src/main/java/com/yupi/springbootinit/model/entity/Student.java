package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName student
 */
@TableName(value ="student")
@Data
public class Student implements Serializable {

    /**
     * 学生班级名
     */
    @TableField(exist = false) // 标注非数据库字段
    private String clazzName;

    /**
     * 学生编号
     */
    @TableId(type = IdType.AUTO)
    private Long studentId;

    /**
     * 名字
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 班级编号
     */
    private Long clazzId;

    /**
     * 作业情况
     */
    private String homeworkStation;

    /**
     * 学习情况
     */
    private String studyStation;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}