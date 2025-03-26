package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName test
 */
@TableName(value ="test")
@Data
public class Test implements Serializable {
    /**
     * 练习题
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 单选题数
     */
    private Integer singleNum;

    /**
     * 多选题数
     */
    private Integer multipleNum;

    /**
     * 判断题数
     */
    private Integer judgeNum;

    /**
     * 简答题数
     */
    private Integer shortAnswerNum;

    /**
     * 是否有答案
     */
    private Boolean isAnswer;

    /**
     * 练习题结果
     */
    private String result;

    /**
     * 目标
     */
    private String goal;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}