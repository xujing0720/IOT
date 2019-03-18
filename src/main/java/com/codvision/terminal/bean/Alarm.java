package com.codvision.terminal.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Alarm {
    private String alarmcode;//告警编码
    private String info1;//告警名称
    private String devicecode;//设备编码
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date alarmtime;//告警时间
    private Integer targettype;//设备类型
    private Integer alarmtype;//告警类型
    private Integer level;//告警等级
    private String place;//告警地点
    private String orgcode;//小区编码
    private String orgname;//小区名称


}

