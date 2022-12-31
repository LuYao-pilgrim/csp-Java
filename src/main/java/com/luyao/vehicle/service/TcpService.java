package com.luyao.vehicle.service;

import com.luyao.vehicle.common.Result;
import com.luyao.vehicle.entity.configDetail.*;

import java.io.IOException;

public interface TcpService {
    Result open();
    Result changeConfig();
    Result changeVDPMConfig(VDPMConfigDetail vdpmConfigDetail);
    Result changeBSMU1BasicConfig(BSMU1BasicConfigDetail bsmu1BasicConfigDetail);
    Result changeBSMU2BasicConfig(BSMU2BasicConfigDetail bsmu2BasicConfigDetail);
    Result changeBSMU1BearingConfig(BSMU1BearingConfigDetail bsmu1BearingConfigDetail);
    Result changeBSMU2BearingConfig(BSMU2BearingConfigDetail bsmu2BearingConfigDetail);
    Result close() throws IOException;
    Result file();
}
