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
    private String shopId;

    public void setType(String tmnType) {
        if (tmnType.equals("smokeDetector")) {
            this.type = "烟感";
        }
        if (tmnType.equals("manholeCover")) {
            this.type = "井盖";
        }
        if (tmnType.equals("electricalSafety")) {
            this.type = "用电安全";
        }
        if (tmnType.equals("combustibleGas")) {
            this.type = "可燃气体";
        }
        if (tmnType.equals("gasmeter")) {
            this.type = "燃气表";
        }
    }

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
                ", status=" + status +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", shopId='" + shopId + '\'' +
                '}';
    }
//    private String orgcode;//小区编码


}
