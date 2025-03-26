package com.yupi.springbootinit.model.dto.prescription;

import lombok.Data;

@Data
public class PrescriptionAddRequest {

    /**
     * 名称
     */
    private String name;

    /**
     * 来源
     */
    private String source;

    /**
     * 组成
     */
    private String composition;

    /**
     * 效果
     */
    private String efficacy;

    /**
     * 主治
     */
    private String therapy;

    /**
     * 用法
     */
    private String dosage;

}
