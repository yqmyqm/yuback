package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName clazz
 */
@TableName(value ="clazz")
@Data
public class Clazz implements Serializable {
    /**
     * 班级编号
     */
    @TableId(type = IdType.AUTO)
    private Long clazzId;

    /**
     * 班级名称
     */
    private String clazzName;

    /**
     * 班级人数
     */
    private Long clazzSize;

    /**
     * 年级
     */
    private String grade;

    /**
     * 专业名称
     */
    private String field;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}