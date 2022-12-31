package com.luyao.vehicle.entity.configDetail;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class BSMU1BasicConfigDetail {
    private String boardNumber;
    private String wheelDiameter;
    private String numGetSpeedGearTooth;
    private String k_emptyVertical;
    private String k_fullVertical;
    private String th_kDifference;
    private String hunting_fs;
    private String hunting_f2;
    private String hunting_f3;
    private String hunting_f4;
    private String hunting_N1;
    private String hunting_N2;
    private String hunting_S;
    private String hunting_TR0;
    private String hunting_TR1;
    private String hunting_TR2;
    private String hunting_D1;
    private String hunting_D;
    private String hunting_H_mag;
    private String hunting_sensitivity;
    private String hunting_nword;
    private String hunting_minspeed;
}
