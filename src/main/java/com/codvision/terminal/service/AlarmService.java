package com.codvision.terminal.service;

import com.codvision.terminal.bean.Alarminfo;

import java.util.List;

public interface AlarmService {

    boolean addAlarm(Alarminfo alarm);

    List<Alarminfo> getAlarm();
}
