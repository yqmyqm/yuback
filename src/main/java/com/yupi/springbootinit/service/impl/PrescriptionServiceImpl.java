package com.yupi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.mapper.PrescriptionMapper;
import com.yupi.springbootinit.model.entity.Prescription ;
import com.yupi.springbootinit.service.PrescriptionService;
import org.springframework.stereotype.Service;

/**
* @author 虞骐铭
* @description 针对表【prescription(药剂)】的数据库操作Service实现
* @createDate 2025-03-24 18:57:37
*/
@Service
public class PrescriptionServiceImpl extends ServiceImpl<PrescriptionMapper, Prescription>
    implements PrescriptionService{

}




