package com.codvision.terminal.service.impl;

import com.codvision.terminal.bean.Device;
import com.codvision.terminal.dao.DeviceMapper;
import com.codvision.terminal.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
     DeviceMapper deviceMapper;
    @Override
    public Device selectDevice(String orgcode) {

        return deviceMapper.selectDevice(orgcode);
    }

    @Override
    public int add(Device device) {

        return deviceMapper.add(device);
    }

    @Override
    public String selectcodeBydeveui(String devEUI) {
        String code=deviceMapper.selectcode(devEUI);
        return code;
    }
}
