package com.luyao.vehicle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luyao.vehicle.entity.file.RowFile;
import com.luyao.vehicle.mapper.RowFileMapper;
import com.luyao.vehicle.service.RowFileService;
import org.springframework.stereotype.Service;

@Service
public class RowFileServiceImpl extends ServiceImpl<RowFileMapper, RowFile> implements RowFileService {
}
