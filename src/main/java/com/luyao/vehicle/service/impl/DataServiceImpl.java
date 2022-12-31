package com.luyao.vehicle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luyao.vehicle.entity.Data;
import com.luyao.vehicle.mapper.DataMapper;
import com.luyao.vehicle.service.DataService;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl extends ServiceImpl<DataMapper, Data> implements DataService {
}
