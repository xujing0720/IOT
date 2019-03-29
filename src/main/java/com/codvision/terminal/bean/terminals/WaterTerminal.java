package com.codvision.terminal.bean.terminals;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class WaterTerminal {
    private String devEUI;
    private String coordType;
    private String lat;
    private String lng;
    private String location;
    private Timestamp onlineTime;
    private int pressureVal;
    private Timestamp reportTime;
    private int status;
    private String tmnDesc;
    private String tmnId;
    private String tmnName;
    private String tmnOIDIndex;
    private int tmnVoltage;
    private String type;

}
