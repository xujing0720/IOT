package com.codvision.terminal.service.impl;

import com.codvision.terminal.bean.Alarm;
import com.codvision.terminal.dao.AlarmMapper;
import com.codvision.terminal.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class AlarmServiceImpl implements AlarmService {
    @Autowired
     private AlarmMapper alarmMapper;
    @Override
    public boolean addAlarm(Alarm alarm) {
        int i=alarmMapper.addAlarm(alarm);
        return i>0?true:false;
    }

    @Override
    public List<Alarm> getAlarm() {

        return alarmMapper.getAlarm();
    }
}
