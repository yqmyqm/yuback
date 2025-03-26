package com.yupi.springbootinit.model.dto.question;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionQueryRequest  extends PageRequest implements Serializable{
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 题目类型
     */
    private String type;

    private static final long serialVersionUID = 1L;
}
