package com.codvision.terminal.service.impl;

import com.codvision.terminal.bean.devices.Device;
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

    @Override
    public int addOrg(String shopId,String code) {
        int v=deviceMapper.addOrg(shopId,code);
        return v;
    }

    @Override
    public int updateLg(Float lat, Float lng) {
        return deviceMapper.updateLg(lat,lng);
    }
}
