package com.codvision.terminal.service;

import com.codvision.terminal.bean.alarms.Alarm;
import com.codvision.terminal.bean.alarms.Alarminfo;

import java.util.List;

public interface AlarmService {

    boolean addAlarm(Alarm alarm);

    List<Alarminfo> getAlarm();

    boolean updateAlarmLg(Alarm alarm);

    int  updateAlarmStatus(Alarm alarm);

    Integer selectAlarmId(String alarmId);

    int addOrg(String shopId,String alarmId);
}
