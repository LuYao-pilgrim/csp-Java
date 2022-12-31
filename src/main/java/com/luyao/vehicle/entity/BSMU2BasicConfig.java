package com.luyao.vehicle.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bsmu2_basic_config")
public class BSMU2BasicConfig {
    private Timestamp datetime;
    private byte[] data;

}
