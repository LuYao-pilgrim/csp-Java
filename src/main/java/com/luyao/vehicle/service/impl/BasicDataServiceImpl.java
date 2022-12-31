package com.luyao.vehicle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luyao.vehicle.entity.BasicData;
import com.luyao.vehicle.mapper.BasicDataMapper;
import com.luyao.vehicle.service.BasicDataService;
import org.springframework.stereotype.Service;

@Service
public class BasicDataServiceImpl extends ServiceImpl<BasicDataMapper, BasicData> implements BasicDataService {
}
