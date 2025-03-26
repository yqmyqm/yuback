package com.yupi.springbootinit.model.dto.clazz;

/**
 * 更新请求
 */
public class ClazzUpdateRequest {
    /**
     * id
     */
    private Long id;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 班级人数
     */
    private Long classSize;

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
