package com.codvision.terminal.controller;

import com.codvision.terminal.bean.Device;
import com.codvision.terminal.bean.alarms.Alarm;
import com.codvision.terminal.bean.alarms.ElectricalSafetyAlarm;
import com.codvision.terminal.bean.alarms.ManholeCoverAlarm;
import com.codvision.terminal.bean.alarms.NewAlarm;
import com.codvision.terminal.bean.terminals.TerminalEx;
import com.codvision.terminal.common.DataResponse;
import com.codvision.terminal.service.AlarmService;
import com.codvision.terminal.service.DeviceService;
import com.codvision.terminal.util.ReplaceUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Lazy(false)
public class DataController {
    private static final String BASE_URL3 = "https://218.108.146.92:10443/restapi/";
    private static final String[] shopId = new String[]{"8", "11", "14", "18", "26"};
    @Autowired
    DeviceService deviceService;
    @Autowired
    AlarmService alarmService;


    @PostConstruct
    @Scheduled(cron = "0 0 0/2 * * *")
    //获取烟感，井盖，用电设备
    public void getTer() {
        Termcontroller termcontroller = new Termcontroller();
        int pageSize = 50;
        int pageNum = 1;
        String[] tmnType = new String[]{"smokeDetector", "manholeCover", "electricalSafety"};
        for (int m = 0; m < shopId.length; m++) {
            for (int n = 0; n < tmnType.length; n++) {
                DataResponse dataResponse = termcontroller.getstwe(shopId[m], tmnType[n], pageNum, pageSize);
                List<TerminalEx> terminalList = new ArrayList<>();
                int total = dataResponse.getTotal();
                for (int j = 1; j < total / pageSize + 2; j++) {
                    System.out.println("第" + j + "页数据");
                    DataResponse<TerminalEx> data = termcontroller.getstwe(shopId[m], tmnType[n], j, pageSize);
                    for (int num = 0; num < data.getList().size(); num++) {
                        terminalList.add(data.getList().get(num));
                    }
                }
                for (int i = 0; i < terminalList.size(); i++) {
                    String s = deviceService.selectcodeBydeveui(terminalList.get(i).getDevEUI());
                    if (s == null) {                                                        //不存在，添加到数据库
                        Device device = new Device();
                        device.setCode(terminalList.get(i).getOidIndex());
                        device.setCreatetime(new Date());
                        device.setUpdatetime(terminalList.get(i).getUpdateTime());
                        device.setLat(terminalList.get(i).getLatitude());
                        device.setLng(terminalList.get(i).getLongitude());
                        device.setType(tmnType[n]);
                        device.setShopId(terminalList.get(i).getShopId());
                        device.setManufacturer(terminalList.get(i).getTmnFacturer());
                        device.setSerialnumber(terminalList.get(i).getDevEUI());
                        device.setModel(terminalList.get(i).getDevType());
                        device.setName(terminalList.get(i).getTmnName());
                        device.setStatus(Integer.parseInt(terminalList.get(i).getOnlineStatus()));
                        device.setDevicetor("何刚");
                        device.setDevicetorMobile("18989457721");
                        deviceService.add(device);                                  //添加到设备数据库
                      int v= deviceService.addOrg(device.getShopId(),device.getCode());              //添加到设备组织
                    } else {
                        System.out.println("此数据已存在" + i);
                    }
                }
            }
        }
    }



    @Scheduled(cron = "0/20 * * * * *")
    //获取烟感告警信息
    public void getSmokerAlarm() {
        Termcontroller termcontroller = new Termcontroller();
        int pageSize = 50;
        int pageNum = 1;
        String tmnType = "smokeDetector";
        for (int m = 0; m < shopId.length; m++) {
            DataResponse<NewAlarm> dataResponse = termcontroller.getSmokerAlarmslist(shopId[m], tmnType, pageNum, pageSize);
            List<NewAlarm> newAlarmList = new ArrayList<>();
            int total = dataResponse.getTotal();
            for (int j = 1; j < total / pageSize + 2; j++) {
                System.out.println("第" + j + "页数据");
                DataResponse<NewAlarm> data = termcontroller.getSmokerAlarmslist(shopId[m], tmnType, j, pageSize);
                for (int num = 0; num < data.getList().size(); num++) {
                    newAlarmList.add(data.getList().get(num));
                }
            }
            for (int i = 0; i < newAlarmList.size(); i++) {
                Integer id = alarmService.selectAlarmId(newAlarmList.get(i).getAlarmId());
                if (id == null || id == 0) {
                    Alarm alarm = new Alarm();
                    alarm.setAlarmId(newAlarmList.get(i).getAlarmId());
                    alarm.setAlarmType(ReplaceUtils.replist(String.join(",", newAlarmList.get(i).getAlarmType())));
                    alarm.setDevEUI(deviceService.selectcodeBydeveui(newAlarmList.get(i).getDevEUI()));
                    alarm.setLocation(newAlarmList.get(i).getLocation());
                    alarm.setDevType(tmnType);
                    alarm.setAlarmname(tmnType);
                    alarm.setAlarmContent("温度："+newAlarmList.get(i).getTemperature()+"℃"+
                            ";电池电压百分比:"+newAlarmList.get(i).getBatteryVoltage()+"%"+
                            ";烟雾浓度百分比:"+newAlarmList.get(i).getSmokeScope()+"%");
                    alarm.setShopId(newAlarmList.get(i).getShopId());
                    alarm.setFirstAlarmTime(newAlarmList.get(i).getFirstAlarmTime());
                    alarm.setShopName(newAlarmList.get(i).getShopName());
                    alarm.setLatitude(newAlarmList.get(i).getLatitude());
                    alarm.setLongitude(newAlarmList.get(i).getLongitude());
                    alarm.setRecoveryTime(newAlarmList.get(i).getRecoveryTime());
                    alarm.setDisposestatus(Integer.parseInt(newAlarmList.get(i).getDisposeStatus()));
                    if (newAlarmList.get(i).getRecoveryStatus() == null) {
                        alarm.setRecoverystatus(1);
                    } else {
                        alarm.setRecoverystatus(Integer.parseInt(newAlarmList.get(i).getRecoveryStatus()));
                    }
                    System.out.println(alarm);
                    alarmService.addAlarm(alarm);                                            //添加到告警
                   int c= alarmService.addOrg(alarm.getShopId(),alarm.getAlarmId());         //添加到告警组织

                } else {
                    System.out.println("此告警数据已存在" + i);
                }
            }
        }

    }



    @Scheduled(cron = "0/20 * * * * *")
    //获取井盖告警信息
    public void getManAlarm() {
        Termcontroller termcontroller = new Termcontroller();
        int pageSize = 50;
        int pageNum = 1;
        String tmnType = "manholeCover";
        for (int m = 0; m < shopId.length; m++) {
            DataResponse<ManholeCoverAlarm> dataResponse = termcontroller.getmanholeCoverAlarmslist(shopId[m], tmnType, pageNum, pageSize);
            List<ManholeCoverAlarm> newAlarmList = new ArrayList<>();
            int total = dataResponse.getTotal();
            for (int j = 1; j < total / pageSize + 2; j++) {
                System.out.println("第" + j + "页数据");
                DataResponse<ManholeCoverAlarm> data = termcontroller.getmanholeCoverAlarmslist(shopId[m], tmnType, j, pageSize);
                for (int num = 0; num < data.getList().size(); num++) {
                    newAlarmList.add(data.getList().get(num));
                }
            }
            for (int i = 0; i < newAlarmList.size(); i++) {
                Integer id = alarmService.selectAlarmId(newAlarmList.get(i).getAlarmId());
                if (id == null || id == 0) {
                    Alarm alarm = new Alarm();
                    alarm.setAlarmId(newAlarmList.get(i).getAlarmId());
                    alarm.setAlarmType(ReplaceUtils.repManAlarm(newAlarmList.get(i).getAlarmType()));
                    alarm.setDevEUI(deviceService.selectcodeBydeveui(newAlarmList.get(i).getDevEUI()));
                    alarm.setLocation(newAlarmList.get(i).getLocation());
                    alarm.setDevType(tmnType);
                    alarm.setAlarmname(tmnType);
                    alarm.setAlarmContent("温度:"+newAlarmList.get(i).getCurrentAngle()+"℃"+
                            ";背景角度:"+newAlarmList.get(i).getBaseAngle()+"°"+
                            ";电池电压百分比:"+newAlarmList.get(i).getCurrentVoltag()+"%");
                    alarm.setRecoveryTime(newAlarmList.get(i).getRecoveryTime());
                    alarm.setShopId(newAlarmList.get(i).getShopId());
                    alarm.setFirstAlarmTime(newAlarmList.get(i).getFirstAlarmTime());
                    alarm.setShopName(newAlarmList.get(i).getShopName());
                    alarm.setLatitude(newAlarmList.get(i).getLatitude());
                    alarm.setLongitude(newAlarmList.get(i).getLongitude());
                    alarm.setDisposestatus(Integer.parseInt(newAlarmList.get(i).getDisposeStatus()));
                    if (newAlarmList.get(i).getRecoveryStatus() == null) {
                        alarm.setRecoverystatus(1);
                    } else {
                        alarm.setRecoverystatus(Integer.parseInt(newAlarmList.get(i).getRecoveryStatus()));
                    }
                    System.out.println(alarm);
                    alarmService.addAlarm(alarm);                           //添加到告警
                    int c= alarmService.addOrg(alarm.getShopId(),alarm.getAlarmId());         //添加到告警组织

                } else {
                    System.out.println("此告警数据已存在" + i);
                }
            }
        }

    }


    @Scheduled(cron = "0/20 * * * * *")
    //获取用电安全告警信息
    public void getElectricalSafetyAlarm() {
        Termcontroller termcontroller = new Termcontroller();
        int pageSize = 50;
        int pageNum = 1;
        String tmnType = "electricalSafety";
        for (int m = 0; m < shopId.length; m++) {
            DataResponse<ElectricalSafetyAlarm> dataResponse = termcontroller.getElectricalSafetyAlarm(shopId[m], tmnType, pageNum, pageSize);
            List<ElectricalSafetyAlarm> newAlarmList = new ArrayList<>();
            int total = dataResponse.getTotal();
            for (int j = 1; j < total / pageSize + 2; j++) {
                System.out.println("第" + j + "页数据");
                DataResponse<ElectricalSafetyAlarm> data = termcontroller.getElectricalSafetyAlarm(shopId[m], tmnType, j, pageSize);
                for (int num = 0; num < data.getList().size(); num++) {
                    newAlarmList.add(data.getList().get(num));
                }
            }
            for (int i = 0; i < newAlarmList.size(); i++) {
                Integer id = alarmService.selectAlarmId(newAlarmList.get(i).getAlarmId());
                if (id == null || id == 0) {
                    Alarm alarm = new Alarm();
                    alarm.setAlarmId(newAlarmList.get(i).getAlarmId());
                    alarm.setAlarmType(ReplaceUtils.repEleAlarm(ArrayUtils.toString(newAlarmList.get(i).getAlarmType())));
                    alarm.setDevEUI(deviceService.selectcodeBydeveui(newAlarmList.get(i).getDevEUI()));//查询设备code
                    alarm.setLocation(newAlarmList.get(i).getLocation());
                    alarm.setDevType(tmnType);
                    alarm.setAlarmname(tmnType);
                    alarm.setAlarmContent("实时告警值:"+((newAlarmList.get(i).getCurrentVal()==null)?"":newAlarmList.get(i).getCurrentVal())+"mA"+
                            ";告警值:"+((newAlarmList.get(i).getAlarmVal()==null)?"":newAlarmList.get(i).getAlarmVal())+"mA");
                    alarm.setShopId(newAlarmList.get(i).getShopId());
                    alarm.setFirstAlarmTime(newAlarmList.get(i).getFirstAlarmTime());
                    alarm.setShopName(newAlarmList.get(i).getShopName());
                    alarm.setLatitude(newAlarmList.get(i).getLatitude());
                    alarm.setLongitude(newAlarmList.get(i).getLongitude());
                    alarm.setRecoveryTime(newAlarmList.get(i).getRecoveryTime());
                    alarm.setDisposestatus(Integer.parseInt(newAlarmList.get(i).getDisposeStatus()));
                    if (newAlarmList.get(i).getRecoveryStatus() == null) {
                        alarm.setRecoverystatus(1);
                    } else {
                        alarm.setRecoverystatus(Integer.parseInt(newAlarmList.get(i).getRecoveryStatus()));
                    }
                    System.out.println(alarm);
                    alarmService.addAlarm(alarm);                           //添加到告警
                    int c= alarmService.addOrg(alarm.getShopId(),alarm.getAlarmId());         //添加到告警组织

                } else {
                    System.out.println("此告警数据已存在" + i);
                }
            }
        }

    }

}