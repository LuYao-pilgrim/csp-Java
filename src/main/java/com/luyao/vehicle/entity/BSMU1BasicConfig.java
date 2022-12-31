package com.luyao.vehicle.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bsmu1_basic_config")
public class BSMU1BasicConfig {
    private Timestamp datetime;
    private byte[] data;

}
