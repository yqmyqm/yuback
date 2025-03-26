package com.yupi.springbootinit.model.dto.prescription;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询方剂请求
 */
@Data
public class PrescriptionQueryRequest extends PageRequest
    implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 来源
     */
    private String source;

    /**
     * 主治
     */
    private String therapy;

    private static final long serialVersionUID = 1L;
}
