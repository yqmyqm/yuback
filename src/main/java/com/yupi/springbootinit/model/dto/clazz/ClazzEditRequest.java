package com.yupi.springbootinit.model.dto.clazz;

import lombok.Data;

/**
 * 编辑请求
 */
@Data
public class ClazzEditRequest {

    /**
     * id
     */
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

    private static final long serialVersionUID = 1L;
}
