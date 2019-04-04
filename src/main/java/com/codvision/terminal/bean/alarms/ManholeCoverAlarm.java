package com.codvision.terminal.bean.alarms;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ManholeCoverAlarm {
    private String currentAngle;//温度
    private String currentVoltag;//电池电压
    private String baseAngle;//背景角度
    private Timestamp firstAlarmTime;//首次告警时间
    private String alarmType;
    private String alarmId;//告警记录Id
    private String shopId;//?场所id
    private String shopName;//场所名称
    private String tmnName;//终端名称
    private String devEUI;//终端sn

    private String coordType;//地图类型
    private Float longitude;//经度
    private Float latitude;//纬度
    private String location;//位置

    private String recoveryStatus;//告警恢复状态
    private String disposeStatus;//告警处理状态

    @Override
    public String toString() {
        return "ManholeCoverAlarm{" +
                "currentAngle='" + currentAngle + '\'' +
                ", currentVoltag='" + currentVoltag + '\'' +
                ", baseAngle='" + baseAngle + '\'' +
                ", firstAlarmTime=" + firstAlarmTime +
                ", alarmType='" + alarmType + '\'' +
                ", alarmId='" + alarmId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", tmnName='" + tmnName + '\'' +
                ", devEUI='" + devEUI + '\'' +
                ", coordType='" + coordType + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", location='" + location + '\'' +
                ", recoveryStatus='" + recoveryStatus + '\'' +
                ", disposeStatus='" + disposeStatus + '\'' +
                ", updateAlarmTime='" + updateAlarmTime + '\'' +
                ", recoveryTime='" + recoveryTime + '\'' +
                '}';
    }

    private String updateAlarmTime;//更新时间
    private Timestamp recoveryTime;//告警恢复时间

}
