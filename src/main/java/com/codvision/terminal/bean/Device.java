package com.codvision.terminal.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Device {
    private String code;//设备编码
    private String name;//设备名称
    private String type;//设备类型
    private String manufacturer;//设备厂商
    private String model;//设备型号
    private String serialnumber;//设备序列号
    private String lng;//经度
    private String lat;//纬度
    private int status;//设备状态
    private Date createtime;//创建时间

    private Date updatetime;//更新时间
//    private String orgcode;//小区编码

    @Override
    public String toString() {
        return "Device{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", serialnumber='" + serialnumber + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", status='" + status + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }



}
