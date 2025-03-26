package com.yupi.springbootinit.model.dto.ai;

import lombok.Data;

@Data
public class GenPlanByAiRequest {

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
}
