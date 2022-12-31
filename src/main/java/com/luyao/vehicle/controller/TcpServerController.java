package com.luyao.vehicle.controller;

import com.luyao.vehicle.common.Result;
import com.luyao.vehicle.entity.configDetail.*;
import com.luyao.vehicle.service.TcpService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName : TcpServerController
 * @Description :
 * @Author : df
 * @Date: 2021-01-07 17:15
 */
@RestController
@RequestMapping("/tcp")
public class TcpServerController {
    @Resource
    private TcpService tcpService;

    @RequestMapping("/open")
    public Result open() {
        return tcpService.open();
    }

    @RequestMapping("/changeConfig")
    public Result changeConfig() {
        return tcpService.changeConfig();
    }

    @RequestMapping("/changeVDPMConfig")
    public Result changeVDPMConfig(VDPMConfigDetail vdpmConfigDetail) {
        return tcpService.changeVDPMConfig(vdpmConfigDetail);
    }

    @RequestMapping("/changeBSMU1BasicConfig")
    public Result changeBSMU1BasicConfig(BSMU1BasicConfigDetail bsmu1BasicConfigDetail) {
        return tcpService.changeBSMU1BasicConfig(bsmu1BasicConfigDetail);
    }

    @RequestMapping("/changeBSMU2BasicConfig")
    public Result changeBSMU1BasicConfig(BSMU2BasicConfigDetail bsmu2BasicConfigDetail) {
        return tcpService.changeBSMU2BasicConfig(bsmu2BasicConfigDetail);
    }

    @RequestMapping("/changeBSMU1BearingConfig")
    public Result changeBSMU1BearingConfig(BSMU1BearingConfigDetail bsmu1BearingConfigDetail) {
        return tcpService.changeBSMU1BearingConfig(bsmu1BearingConfigDetail);
    }

    @RequestMapping("/changeBSMU2BearingConfig")
    public Result changeBSMU1BearingConfig(BSMU2BearingConfigDetail bsmu2BearingConfigDetail) {
        return tcpService.changeBSMU2BearingConfig(bsmu2BearingConfigDetail);
    }

    @RequestMapping("file")
    public Result send() {
        return tcpService.file();
    }

    @RequestMapping("/close")
    public Result close() throws IOException {
        return tcpService.close();
    }
}

