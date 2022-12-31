package com.luyao.vehicle.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bsmu1_bearing_config")
public class BSMU1BearingConfig {
    private Timestamp datetime;
    private byte[] data;

}
