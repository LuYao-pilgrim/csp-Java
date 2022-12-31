package com.luyao.vehicle.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luyao.vehicle.entity.*;
import com.luyao.vehicle.mapper.VDPMConfigMapper;
import com.luyao.vehicle.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
public class ConfigController {

    @Autowired
    VDPMConfigService vdpmConfigService;

    @Autowired
    BSMU1BasicConfigService bsmu1BasicConfigService;

    @Autowired
    BSMU1BearingConfigService bsmu1BearingConfigService;

    @Autowired
    BSMU2BasicConfigService bsmu2BasicConfigService;

    @Autowired
    BSMU2BearingConfigService bsmu2BearingConfigService;

    @GetMapping("/vdpmConfig")
    public byte[] vdpmConfig(){
        VDPMConfig vdpmConfig = vdpmConfigService.getOne(new QueryWrapper<VDPMConfig>().orderByDesc("datetime").last("limit 1"));
        byte[] bytes = vdpmConfig.getData();

        return bytes;
    }

    @GetMapping("/bsmu1basicConfig")
    public byte[] bsmu1basicConfig(){
        BSMU1BasicConfig bsmu1BasicConfig = bsmu1BasicConfigService.getOne(new QueryWrapper<BSMU1BasicConfig>().orderByDesc("datetime").last("limit 1"));
        byte[] bytes = bsmu1BasicConfig.getData();

        return bytes;
    }

    @GetMapping("/bsmu1bearingConfig")
    public byte[] bsmu1bearingConfig(){
        BSMU1BearingConfig bsmu1BearingConfig = bsmu1BearingConfigService.getOne(new QueryWrapper<BSMU1BearingConfig>().orderByDesc("datetime").last("limit 1"));
        byte[] bytes = bsmu1BearingConfig.getData();

        return bytes;
    }

    @GetMapping("/bsmu2basicConfig")
    public byte[] bsmu2basicConfig(){
        BSMU2BasicConfig bsmu2BasicConfig = bsmu2BasicConfigService.getOne(new QueryWrapper<BSMU2BasicConfig>().orderByDesc("datetime").last("limit 1"));
        byte[] bytes = bsmu2BasicConfig.getData();

        return bytes;
    }

    @GetMapping("/bsmu2bearingConfig")
    public byte[] bsmu2bearingConfig(){
        BSMU2BearingConfig bsmu2BearingConfig = bsmu2BearingConfigService.getOne(new QueryWrapper<BSMU2BearingConfig>().orderByDesc("datetime").last("limit 1"));
        byte[] bytes = bsmu2BearingConfig.getData();

        return bytes;
    }

}
