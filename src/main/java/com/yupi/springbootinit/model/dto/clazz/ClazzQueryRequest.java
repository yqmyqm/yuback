package com.yupi.springbootinit.model.dto.clazz;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClazzQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

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
