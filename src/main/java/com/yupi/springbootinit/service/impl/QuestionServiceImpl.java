package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.entity.Question;
import com.yupi.springbootinit.service.QuestionService;
import com.yupi.springbootinit.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author 虞骐铭
* @description 针对表【question】的数据库操作Service实现
* @createDate 2025-03-26 12:08:57
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




