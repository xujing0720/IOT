package com.codvision.terminal.bean;

import lombok.Data;

import java.util.UUID;

@Data
public class Alarminfo {
    private String alarmId;
    private Number alarmTime; //告警时间，unix 时间戳
    private String alarmType; //告警类型 0 正常 1 报警
    private int buzzerStatus;
    private String date;//告警时间
    private String devEUI;//设备 EUI 号
    private String devType; //终端类型
    private String fra; //火灾报警 0 正常 1 报警
    private String latitude; //纬度
    private String location; //地理位置
    private String longitude;//经度
    private String mlba; //无线底座低电压报警 0 正常 1 报警
    private String mofa; //无线底座其他故障报警 0 正常 1 报警
    private String muteAlarm;
    private UUID oIDIndex;//终端号
    private String scenarioId; //场所 ID
    private String sfa; //独立式烟感故障报警 0 正常 1 报警
    private String slba; //独立式烟感低电压报警 0 正常 1 报警
    private String slfa; //独立式烟感失联报警 0 正常 1 报警
    private String soa;//无线底座防拆报警 0 正常 1 报警
    private String sst; //独立式烟感手动自检标志 0 正常 1 报警
    private String temperature; //温度值
    private int terminalBatteryVoltage;
    private int terminalSmokescope;
    private String tpa; //无线底座温度超限报警 0 正常 1 报警
    private String tsfa; //无线底座温度传感故障 0 正常 1 报警
 //   private Object oIDIndex;
}

