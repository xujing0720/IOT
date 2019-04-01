package com.codvision.terminal.service;

import com.codvision.terminal.bean.Device;

public interface DeviceService {
    Device selectDevice(String orgcode);

    int add(Device device);

    String selectcodeBydeveui(String devEUI);
}
