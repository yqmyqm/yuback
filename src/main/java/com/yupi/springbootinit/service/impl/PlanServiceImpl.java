package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.entity.Plan ;
import com.yupi.springbootinit.service.PlanService;
import com.yupi.springbootinit.mapper.PlanMapper;
import org.springframework.stereotype.Service;

/**
* @author 虞骐铭
* @description 针对表【plan】的数据库操作Service实现
* @createDate 2025-03-22 13:20:22
*/
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan>
    implements PlanService{

}




