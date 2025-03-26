package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.entity.Chat;
import com.yupi.springbootinit.service.ChatService;
import com.yupi.springbootinit.mapper.ChatMapper;
import org.springframework.stereotype.Service;

/**
* @author 虞骐铭
* @description 针对表【chat】的数据库操作Service实现
* @createDate 2025-03-21 21:44:01
*/
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat>
    implements ChatService{

}




