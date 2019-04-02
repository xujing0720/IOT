package com.codvision.terminal.bean.alarms;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ElectricalSafetyAlarm {
    private String alarmDetailType;
    private String currentVal;
    private String msgId;
    private String type;
    private Timestamp alarmDate;
    private int length;
    private String alarmName;
    private String tmnOIDIndex;
    private String appEUI;
    private String updateAlarmTime;//更新时间
    private Timestamp recoveryTime;//告警恢复时间

    private Timestamp firstAlarmTime;//首次告警时间
    private String alarmId;//告警记录Id
    private String shopId;//?场所id
    private String shopName;//场所名称
    private String tmnName;//终端名称
    private String devEUI;//终端sn
    private String terminalType;
    private String longitude;//经度
    private String latitude;//纬度
    private String location;//位置
    private String alarmType;
    private String recoveryStatus;
    private String disposeStatus;

    @Override
    public String toString() {
        return "ElectricalSafetyAlarm{" +
                "alarmDetailType='" + alarmDetailType + '\'' +
                ", currentVal='" + currentVal + '\'' +
                ", msgId='" + msgId + '\'' +
                ", type='" + type + '\'' +
                ", alarmDate=" + alarmDate +
                ", length=" + length +
                ", alarmName='" + alarmName + '\'' +
                ", tmnOIDIndex='" + tmnOIDIndex + '\'' +
                ", appEUI='" + appEUI + '\'' +
                ", updateAlarmTime='" + updateAlarmTime + '\'' +
                ", recoveryTime='" + recoveryTime + '\'' +
                ", firstAlarmTime=" + firstAlarmTime +
                ", alarmId='" + alarmId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", tmnName='" + tmnName + '\'' +
                ", devEUI='" + devEUI + '\'' +
                ", terminalType='" + terminalType + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", location='" + location + '\'' +
                ", alarmType='" + alarmType + '\'' +
                ", recoveryStatus=" + recoveryStatus +
                ", disposeStatus=" + disposeStatus +
                '}';
    }
}
