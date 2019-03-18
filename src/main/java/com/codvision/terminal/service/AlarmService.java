package com.codvision.terminal.service;

import com.codvision.terminal.bean.Alarm;

import java.util.List;

public interface AlarmService {

    boolean addAlarm(Alarm alarm);

    List<Alarm> getAlarm();
}
