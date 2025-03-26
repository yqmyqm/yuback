package com.yupi.springbootinit.model.dto.question;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionAddRequest implements Serializable {

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目类型
     */
    private String type;

    /**
     * 题目科目
     */
    private String subject;

    /**
     * 题目
     */
    private String question;

    /**
     * 答案
     */
    private String answer;

    private static final long serialVersionUID = 1L;
}
