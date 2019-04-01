package com.codvision.terminal.bean.terminals;

import lombok.Data;

import java.sql.Timestamp;

@Data
//地磁设备
public class GeoTerminal {
    private String _id;
   private String oId;
   private  int __v;
   private String battery;
   private String lowBatteryStatus;
   private Timestamp updateTime;
   private int voltage;
   private String terminalId;
   private String devEUI;
   private String scenarioId;
   private String appEUI;
   private String parkingLotStatus;
   private String longitude;
   private String latitude;
   private String parkingLotNumber;
   private Timestamp startTime;
   private int batteryVoltage;

}
