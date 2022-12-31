package com.luyao.vehicle.entity.file;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("row_file")
public class RowFile {
    private Timestamp datetime;
    private byte[] data;
}
