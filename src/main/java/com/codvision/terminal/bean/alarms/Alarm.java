package com.codvision.terminal.bean.alarms;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class Alarm {
    private String alarmId;//告警记录Id
    private String shopId;//场所id
    private String shopName;//场所名称

    @Override
    public String toString() {
        return "Alarm{" +
                "alarmId='" + alarmId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", devType='" + devType + '\'' +
                ", devEUI='" + devEUI + '\'' +
                ", alarmType='" + alarmType + '\'' +
                ", location='" + location + '\'' +
                ", firstAlarmTime=" + firstAlarmTime +
                ", alarmname='" + alarmname + '\'' +
                '}';
    }

    private String devType;//终端类型
    private String devEUI;//终端sn
    private String alarmType;//告警类型
    private String location;//位置
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date firstAlarmTime;//首次告警时间
    private String alarmname;//告警名称

    public void setAlarmname(String tmnType) {
        if (tmnType.equals("smokeDetector")) {
            this.alarmname = "烟感";
        }
        if (tmnType.equals("manholeCover")) {
            this.alarmname = "井盖";
        }
        if (tmnType.equals("electricalSafety")) {
            this.alarmname = "用电安全";
        }
        if (tmnType.equals("combustibleGas")) {
            this.alarmname = "可燃气体";
        }
        if (tmnType.equals("gasmeter")) {
            this.alarmname = "燃气表";
        }
    }

    public void setDevType(String tmnType) {
        if (tmnType.equals("smokeDetector")) {
            this.devType = "烟感";
        }
        if (tmnType.equals("manholeCover")) {
            this.devType = "井盖";
        }
        if (tmnType.equals("electricalSafety")) {
            this.devType = "用电安全";
        }
        if (tmnType.equals("combustibleGas")) {
            this.devType = "可燃气体";
        }
        if (tmnType.equals("gasmeter")) {
            this.devType = "燃气表";
        }
        this.devType = tmnType;

    }
}
