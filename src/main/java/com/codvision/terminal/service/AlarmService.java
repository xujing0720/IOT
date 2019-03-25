package com.codvision.terminal.service;

import com.codvision.terminal.bean.Alarm;
import com.codvision.terminal.bean.Alarminfo;

import java.util.List;

public interface AlarmService {

    boolean addAlarm(Alarm alarm);

    List<Alarminfo> getAlarm();
}
