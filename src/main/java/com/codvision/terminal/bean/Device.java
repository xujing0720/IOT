package com.codvision.terminal.bean;

import lombok.Data;

@Data
public class Device {
    private String code;//设备编码
    private String name;//设备名称
    private String type;//设备类型
    private String lng;//经度
    private String lat;//纬度
    private String status;//设备状态
    private String orgcode;//小区编码
    private String orgname;//小区名称
}
