package com.yupi.springbootinit.model.dto.ai;

import lombok.Data;

@Data
public class GenTestByAiRequest {
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
     * 目标
     */
    private String goal;

    /**
     * 是否生成答案
     */
    private Boolean isGenerateAnswer;

}
