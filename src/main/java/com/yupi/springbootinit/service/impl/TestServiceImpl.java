package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.entity.Test;
import com.yupi.springbootinit.service.TestService;
import com.yupi.springbootinit.mapper.TestMapper;
import org.springframework.stereotype.Service;

/**
* @author 虞骐铭
* @description 针对表【test】的数据库操作Service实现
* @createDate 2025-03-26 13:25:06
*/
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test>
    implements TestService{

}




