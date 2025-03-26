package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 药剂
 * @TableName prescription
 */
@TableName(value ="prescription")
@Data
public class Prescription implements Serializable {
    /**
     * 药剂id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 药剂名称
     */
    private String name;

    /**
     * 药剂来源
     */
    private String source;

    /**
     * 药剂组成
     */
    private String composition;

    /**
     * 药剂功效
     */
    private String efficacy;

    /**
     * 方剂主治
     */
    private String therapy;

    /**
     * 药剂用量
     */
    private String dosage;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
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