package com.codvision.terminal.bean.alarms;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NewAlarm {
    private String alarmId;//告警记录Id
    private String shopId;//?场所id
    private String shopName;//场所名称
    private String tmnName;//终端名称
    private String devEUI;//终端sn
    private String[] alarmType;//告警类型
    private String coordType;//地图类型
    private String longitude;//经度
    private String latitude;//纬度
    private String location;//位置
    private String temperature;//温度
    private String batteryVoltage;//电池电压百分比
    private String smokeScope;//烟雾浓度百分比
    private String recoveryStatus;//告警恢复状态
    private String disposeStatus;//告警处理状态
    private Timestamp firstAlarmTime;//首次告警时间
    private String updateAlarmTime;//更新时间
    private Timestamp recoveryTime;//告警恢复时间
}
