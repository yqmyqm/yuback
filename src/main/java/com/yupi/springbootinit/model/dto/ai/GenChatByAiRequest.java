package com.yupi.springbootinit.model.dto.ai;

import lombok.Data;

/**
 * 对Ai发送的聊天请求
 */
@Data
public class GenChatByAiRequest {

    /**
     * 生成聊天数据的名称
     */
    private String name;

    /**
     * 生成的目标
     */
    private String goal;


}
