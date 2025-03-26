package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName plan
 */
@TableName(value ="plan")
@Data
public class Plan implements Serializable {
    /**
     * 教案id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 专业学科
     */
    private String subject;

    /**
     * 教材名称
     */
    private String bookName;

    /**
     * 总课时
     */
    private String classTime;

    /**
     * 课题
     */
    private String topic;

    /**
     * 生成结果
     */
    private String result;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}