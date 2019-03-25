package com.codvision.terminal.bean;

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

}
