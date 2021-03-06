package com.codvision.terminal.service;

import com.codvision.terminal.bean.devices.Device;

public interface DeviceService {
    Device selectDevice(String orgcode);

    int add(Device device);

    String selectcodeBydeveui(String devEUI);

    int addOrg(String shopId,String code);

    int updateLg(Float lat, Float lng);
}
