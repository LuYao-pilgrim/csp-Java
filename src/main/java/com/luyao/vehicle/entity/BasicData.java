package com.luyao.vehicle.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("vdpm_basic_table")
public class BasicData {
    private Timestamp datetime;
    private byte[] data;
}
