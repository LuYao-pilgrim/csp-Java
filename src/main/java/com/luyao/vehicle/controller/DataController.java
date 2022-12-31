package com.luyao.vehicle.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luyao.vehicle.entity.BSMU1Data;
import com.luyao.vehicle.entity.BSMU2Data;
import com.luyao.vehicle.entity.BasicData;
import com.luyao.vehicle.entity.Data;
import com.luyao.vehicle.service.BSMU1DataService;
import com.luyao.vehicle.service.BSMU2DataService;
import com.luyao.vehicle.service.BasicDataService;
import com.luyao.vehicle.service.DataService;
import com.luyao.vehicle.utils.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;


@RestController
public class DataController {

    @Autowired
    DataService dataService;

    @Autowired
    BasicDataService basicDataService;

    @Autowired
    BSMU1DataService bsmu1DataService;

    @Autowired
    BSMU2DataService bsmu2DataService;

    @GetMapping("/VDPMcondition")
    public byte[] vdpm(){
        Data data = dataService.getOne(new QueryWrapper<Data>().orderByDesc("datetime").last("limit 1"));
        byte[] bytes = data.getData();
        return bytes;
    }

    @GetMapping("/VDPMbasiccondition")
    public byte[] vdpmbasic(){
        BasicData basicdata = basicDataService.getOne(new QueryWrapper<BasicData>().orderByDesc("datetime").last("limit 1"));
        byte[] bytes = basicdata.getData();

        return bytes;
    }

    @GetMapping("/BSMU1condition")
    public byte[] bsmu1(){
        BSMU1Data bsmu1Data = bsmu1DataService.getOne(new QueryWrapper<BSMU1Data>().orderByDesc("datetime").last("limit 1"));
        byte[] bytes = bsmu1Data.getData();

        return bytes;
    }

    @GetMapping("/BSMU2condition")
    public byte[] bsmu2(){
        BSMU2Data bsmu2Data = bsmu2DataService.getOne(new QueryWrapper<BSMU2Data>().orderByDesc("datetime").last("limit 1"));
        byte[] bytes = bsmu2Data.getData();

        return bytes;
    }

}
