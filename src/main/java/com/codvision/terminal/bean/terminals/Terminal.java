package com.codvision.terminal.bean.terminals;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Terminal {

    private  Float latitude;
    private String location;
    private Float longitude;
    private String shopId;
    private String onlineStatus;
    private String appEUI;
    private String tmnName;
    private String oidIndex;
    private String devType;
    private String devEUI;
    private String tmnFacturer;
    private String coordType;
    private String appKey;
    private Timestamp updateTime;


    @Override
    public String toString() {
        return "Terminal{" +
                "latitude='" + latitude + '\'' +
                ", location='" + location + '\'' +
                ", longitude='" + longitude + '\'' +
                ", shopId='" + shopId + '\'' +
                ", onlineStatus='" + onlineStatus + '\'' +
                ", appEUI='" + appEUI + '\'' +
                ", tmnName='" + tmnName + '\'' +
                ", oidIndex='" + oidIndex + '\'' +
                ", devType='" + devType + '\'' +
                ", devEUI='" + devEUI + '\'' +
                ", tmnFacturer='" + tmnFacturer + '\'' +
                ", coordType='" + coordType + '\'' +
                ", appKey='" + appKey + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}









