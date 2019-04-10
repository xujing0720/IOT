package com.codvision.terminal.controller;

import com.codvision.terminal.bean.alarms.Alarm;
import com.codvision.terminal.bean.alarms.ElectricalSafetyAlarm;
import com.codvision.terminal.bean.alarms.ManholeCoverAlarm;
import com.codvision.terminal.bean.alarms.NewAlarm;
import com.codvision.terminal.bean.devices.Device;
import com.codvision.terminal.bean.terminals.GeoTerminal;
import com.codvision.terminal.bean.terminals.TerminalEx;
import com.codvision.terminal.bean.terminals.WaterTerminal;
import com.codvision.terminal.bean.zuobiao.Gps;
import com.codvision.terminal.common.DataResponse;
import com.codvision.terminal.service.AlarmService;
import com.codvision.terminal.service.DeviceService;
import com.codvision.terminal.util.DandF;
import com.codvision.terminal.util.PositionUtil;
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
    /**
    * @Description: 获取烟感，井盖，用电设备,燃气表
    * @Param: []
    * @return: void
    * @Author: XJ
    * @Date: 2019/4/9
    */
    public void getTer() {
        Termcontroller termcontroller = new Termcontroller();
        int pageSize = 50;
        int pageNum = 1;
        String[] tmnType = new String[]{"smokeDetector", "manholeCover", "electricalSafety", "gasmeter"};
        for (int m = 0; m < shopId.length; m++) {
            for (int n = 0; n < tmnType.length; n++) {
                DataResponse dataResponse = termcontroller.getstwe(shopId[m], tmnType[n], pageNum, pageSize);
                List<TerminalEx> terminalList = new ArrayList<>();
                int total = dataResponse.getTotal();
                for (int j = 1; j < total / pageSize + 2; j++) {
                    // System.out.println("第" + j + "页数据");
                    DataResponse<TerminalEx> data = termcontroller.getstwe(shopId[m], tmnType[n], j, pageSize);
                    for (int num = 0; num < data.getList().size(); num++) {
                        terminalList.add(data.getList().get(num));
                    }
                }
                for (int i = 0; i < terminalList.size(); i++) {
                    String s = deviceService.selectcodeBydeveui(terminalList.get(i).getDevEUI());

                    Gps gps = new Gps();
                    Device device = new Device();
                    device.setCode(terminalList.get(i).getOidIndex());
                    device.setCreatetime(new Date());
                    device.setUpdatetime(terminalList.get(i).getUpdateTime());
                    String coordType = terminalList.get(i).getCoordType();
                   // System.out.println(coordType);
                    if (terminalList.get(i).getLatitude() != null) {
                        if (coordType == null || coordType.equals("bd-09")) {
                            gps = PositionUtil.bd09_To_Gps84(terminalList.get(i).getLatitude(), terminalList.get(i).getLongitude());
                        } else if (coordType.equals("wgs84")) {
                            System.out.println(terminalList.get(i).getLatitude());
                            gps.setWgLat(DandF.flTodo(terminalList.get(i).getLatitude()));
                            gps.setWgLon(DandF.flTodo(terminalList.get(i).getLongitude()));
                        } else if (coordType.equals("gcj-02")) {
                            gps = PositionUtil.gcj_To_Gps84(terminalList.get(i).getLatitude(), terminalList.get(i).getLongitude());
                        }
                        device.setLat(DandF.pase(gps.getWgLat()));
                        device.setLng(DandF.pase(gps.getWgLon()));
                    } else {
                        device.setLat(null);
                        device.setLng(null);
                    }
                    device.setType(tmnType[n]);
                    device.setShopId(terminalList.get(i).getShopId());
                    device.setManufacturer(terminalList.get(i).getTmnFacturer());
                    device.setSerialnumber(terminalList.get(i).getDevEUI());
                    device.setModel(terminalList.get(i).getDevType());
                    device.setName(terminalList.get(i).getTmnName());
                    device.setStatus(Integer.parseInt(terminalList.get(i).getOnlineStatus()));
                    device.setDevicetor("何刚");
                    device.setDevicetorMobile("18989457721");
                   // System.out.println(device);
                    deviceService.add(device);          //不存在，添加到数据库                //添加到设备数据库
                    if (s == null) {
                        int v = deviceService.addOrg(device.getShopId(), device.getCode());              //添加到设备组织
                    } else {
                     //   System.out.println("此数据已存在" + i);
                    }
                }
            }
        }
    }

    @PostConstruct
    @Scheduled(cron = "0 0 0/2 * * *")
    //获取水压设备
    public void getwaterPre() {
        Termcontroller termcontroller = new Termcontroller();
        int pageSize = 50;
        int pageNum = 1;
        int[] status = new int[]{0, 1, 2};
        for (int m = 0; m < shopId.length; m++) {
            for (int n = 0; n < status.length; n++) {
                DataResponse dataResponse = termcontroller.getWaterpressure(shopId[m], status[n], pageNum, pageSize);
                List<WaterTerminal> terminalList = new ArrayList<>();
                int total = dataResponse.getTotal();
                for (int j = 1; j < total / pageSize + 2; j++) {
                    //   System.out.println("第" + j + "页数据");
                    DataResponse<WaterTerminal> data = termcontroller.getWaterpressure(shopId[m], status[n], j, pageSize);
                    for (int num = 0; num < data.getList().size(); num++) {
                        terminalList.add(data.getList().get(num));
                    }
                }
                for (int i = 0; i < terminalList.size(); i++) {
                    String s = deviceService.selectcodeBydeveui(terminalList.get(i).getDevEUI());
                    Device device = new Device();
                    Gps gps = new Gps();
                    device.setCode(terminalList.get(i).getTmnOIDIndex());
                    device.setCreatetime(new Date());
                    device.setUpdatetime(terminalList.get(i).getReportTime());
                    String coordType = terminalList.get(i).getCoordType();
                    //System.out.println(coordType);
                    if (terminalList.get(i).getLat() != null) {
                        if (coordType == null || coordType.equals("gcj-02")) {
                            gps = PositionUtil.gcj_To_Gps84(terminalList.get(i).getLat(), terminalList.get(i).getLng());
                        } else if (coordType.equals("wgs84")) {
                          //  System.out.println(terminalList.get(i).getLat());
                            gps.setWgLat(DandF.flTodo(terminalList.get(i).getLat()));
                            gps.setWgLon(DandF.flTodo(terminalList.get(i).getLng()));
                        } else if (coordType.equals("bd-09")) {
                            gps = PositionUtil.bd09_To_Gps84(terminalList.get(i).getLat(), terminalList.get(i).getLng());
                        }
                        device.setLat(DandF.pase(gps.getWgLat()));
                        device.setLng(DandF.pase(gps.getWgLon()));
                    } else {
                        device.setLat(null);
                        device.setLng(null);
                    }
                    device.setType("水压");
                    device.setShopId(shopId[m]);
                    device.setManufacturer("杭州拓深科技有限公司");
                    device.setSerialnumber(terminalList.get(i).getDevEUI());
                    device.setModel(terminalList.get(i).getType());
                    device.setName(terminalList.get(i).getTmnName());
                    if (terminalList.get(i).getStatus() == 2) {
                        device.setStatus(1);
                    } else if (terminalList.get(i).getStatus() == 1) {
                        device.setStatus(0);
                    } else if (terminalList.get(i).getStatus() == 0) {
                        device.setStatus(2);
                    }
                    device.setDevicetor("徐靖");
                    device.setDevicetorMobile("18342803621");
                    deviceService.add(device); //不存在，添加到数据库
                    if (s == null) {
                        int v = deviceService.addOrg(device.getShopId(), device.getCode());              //添加到设备组织
                    } else {
                        //  deviceService.updateLg(device.getLat(), device.getLng());
                       // System.out.println("此数据已存在" + i);
                    }
                }
            }
        }

    }

    @PostConstruct
    @Scheduled(cron = "0 0 0/2 * * *")
    //获取地磁设备
    public void getDici() {
        Termcontroller termcontroller = new Termcontroller();
        for (int m = 0; m < shopId.length; m++) {
            DataResponse<GeoTerminal> data = termcontroller.getDici(shopId[m]);
            //  System.out.println(data);
            List<GeoTerminal> terminalList = new ArrayList<>();
            for (int num = 0; num < data.getList().size(); num++) {
                terminalList.add(data.getList().get(num));
            }
            for (int i = 0; i < terminalList.size(); i++) {
                String s = deviceService.selectcodeBydeveui(terminalList.get(i).getDevEUI());

                Gps gps = new Gps();
                Device device = new Device();
                device.setCode(terminalList.get(i).getOId());
                device.setCreatetime(new Date());
                device.setUpdatetime(terminalList.get(i).getUpdateTime());
                String coordType = terminalList.get(i).getCoordType();
          //      System.out.println(coordType);
                if (terminalList.get(i).getLatitude() != null) {
                    if (coordType == null || coordType.equals("bd-09")) {
                        gps = PositionUtil.bd09_To_Gps84(terminalList.get(i).getLatitude(), terminalList.get(i).getLongitude());
                    } else if (coordType.equals("wgs84")) {
                      //  System.out.println(terminalList.get(i).getLatitude());
                        gps.setWgLat(DandF.flTodo(terminalList.get(i).getLatitude()));
                        gps.setWgLon(DandF.flTodo(terminalList.get(i).getLongitude()));
                    } else if (coordType.equals("gcj-02")) {
                        gps = PositionUtil.gcj_To_Gps84(terminalList.get(i).getLatitude(), terminalList.get(i).getLongitude());
                    }
                    device.setLat(DandF.pase(gps.getWgLat()));
                    device.setLng(DandF.pase(gps.getWgLon()));
                } else {
                    device.setLat(null);
                    device.setLng(null);
                }
                device.setType("地磁");
                device.setShopId(shopId[m]);
                device.setManufacturer("中星测控");
                device.setSerialnumber(terminalList.get(i).getDevEUI());
                device.setModel("地磁测试");
                device.setName("华数园区");
                device.setStatus(1);
                device.setDevicetor("徐靖");
                device.setDevicetorMobile("18342803621");
                deviceService.add(device);//不存在，添加到数据库,有则更新
                if (s == null) {
                    int v = deviceService.addOrg(device.getShopId(), device.getCode());              //添加到设备组织
                }
            }
        }
    }

    @PostConstruct
    @Scheduled(cron = "0 0 0/2 * * *")
    //获取烟感告警信息
    public void getSmokerAlarm() {
        long endTime = System.currentTimeMillis();
        long startTime = endTime -  1000 * 60 * 60 * 24 * 30* 9;
        Termcontroller termcontroller = new Termcontroller();
        int pageSize = 50;
        int pageNum = 1;
        String tmnType = "smokeDetector";
        for (int m = 0; m < shopId.length; m++) {
            DataResponse<NewAlarm> dataResponse = termcontroller.getSmokerAlarmslist(shopId[m], tmnType, pageNum, pageSize, startTime, endTime);
            List<NewAlarm> newAlarmList = new ArrayList<>();
            int total = dataResponse.getTotal();
            for (int j = 1; j < total / pageSize + 2; j++) {
                //  System.out.println("第" + j + "页数据");
                DataResponse<NewAlarm> data = termcontroller.getSmokerAlarmslist(shopId[m], tmnType, j, pageSize, startTime, endTime);
                for (int num = 0; num < data.getList().size(); num++) {
                    newAlarmList.add(data.getList().get(num));
                }
            }
            for (int i = 0; i < newAlarmList.size(); i++) {
                Integer id = alarmService.selectAlarmId(newAlarmList.get(i).getAlarmId());
                String s = deviceService.selectcodeBydeveui(newAlarmList.get(i).getDevEUI()); //判断设备有没有添加

                Alarm alarm = new Alarm();
                Gps gps = new Gps();
                alarm.setAlarmId(newAlarmList.get(i).getAlarmId());
                alarm.setAlarmType(ReplaceUtils.replist(String.join(",", newAlarmList.get(i).getAlarmType())));
                alarm.setDevEUI(s);
                alarm.setLocation(newAlarmList.get(i).getLocation());
                alarm.setDevType(tmnType);
                alarm.setAlarmname(tmnType);
                alarm.setAlarmContent("温度：" + newAlarmList.get(i).getTemperature() + "℃" +
                        ";电池电压百分比:" + newAlarmList.get(i).getBatteryVoltage() + "%" +
                        ";烟雾浓度百分比:" + newAlarmList.get(i).getSmokeScope() + "%");
                alarm.setShopId(newAlarmList.get(i).getShopId());
                alarm.setFirstAlarmTime(newAlarmList.get(i).getFirstAlarmTime());
                alarm.setShopName(newAlarmList.get(i).getShopName());
                String coordType = newAlarmList.get(i).getCoordType();
                if (newAlarmList.get(i).getLatitude() != null) {
                    if (coordType == null || coordType.equals("bd-09")) {
                        gps = PositionUtil.bd09_To_Gps84(newAlarmList.get(i).getLatitude(), newAlarmList.get(i).getLongitude());
                    } else if (coordType.equals("wgs84")) {
                      //  System.out.println(newAlarmList.get(i).getLatitude());
                        gps.setWgLat(DandF.flTodo(newAlarmList.get(i).getLatitude()));
                        gps.setWgLon(DandF.flTodo(newAlarmList.get(i).getLongitude()));
                    } else if (coordType.equals("gcj-02")) {
                        gps = PositionUtil.gcj_To_Gps84(newAlarmList.get(i).getLatitude(), newAlarmList.get(i).getLongitude());
                    }
                    alarm.setLatitude(DandF.pase(gps.getWgLat()));
                    alarm.setLongitude(DandF.pase(gps.getWgLon()));
                } else {
                    alarm.setLatitude(null);
                    alarm.setLongitude(null);
                }
                alarm.setRecoveryTime(newAlarmList.get(i).getRecoveryTime());
                alarm.setDisposestatus(Integer.parseInt(newAlarmList.get(i).getDisposeStatus()));
                if (newAlarmList.get(i).getRecoveryStatus() == null) {
                    alarm.setRecoverystatus(1);
                } else {
                    alarm.setRecoverystatus(Integer.parseInt(newAlarmList.get(i).getRecoveryStatus()));
                }
                alarmService.addAlarm(alarm);
                //  System.out.println(alarm);
                if (s != null && id == null) {                           //设备添加了，但告警信息没存
                    //添加到告警
                    int c = alarmService.addOrg(alarm.getShopId(), alarm.getAlarmId());         //添加到告警组织

                } else {
                    // System.out.println("此告警数据已存在或者设备还未添加" + i);
                }
            }
        }

    }


    @PostConstruct
    @Scheduled(cron = "0 0 0/2 * * *")
    //获取井盖告警信息
    public void getManAlarm() {
        long endTime = System.currentTimeMillis();
        long startTime = endTime -  1000 * 60 * 60 * 24 * 30* 9;
        Termcontroller termcontroller = new Termcontroller();
        int pageSize = 50;
        int pageNum = 1;
        String tmnType = "manholeCover";
        for (int m = 0; m < shopId.length; m++) {
            DataResponse<ManholeCoverAlarm> dataResponse = termcontroller.getmanholeCoverAlarmslist(shopId[m], tmnType, pageNum, pageSize, startTime, endTime);
            List<ManholeCoverAlarm> newAlarmList = new ArrayList<>();
            int total = dataResponse.getTotal();
         //   System.out.println(total);
            for (int j = 1; j < total / pageSize + 2; j++) {
                // System.out.println("第" + j + "页数据");
                DataResponse<ManholeCoverAlarm> data = termcontroller.getmanholeCoverAlarmslist(shopId[m], tmnType, j, pageSize, startTime, endTime);
                for (int num = 0; num < data.getList().size(); num++) {
                    newAlarmList.add(data.getList().get(num));
                }
            }
            for (int i = 0; i < newAlarmList.size(); i++) {
                Integer id = alarmService.selectAlarmId(newAlarmList.get(i).getAlarmId());
                String s = deviceService.selectcodeBydeveui(newAlarmList.get(i).getDevEUI()); //判断设备有没有添加

                    Alarm alarm = new Alarm();
                    Gps gps = new Gps();
                    alarm.setAlarmId(newAlarmList.get(i).getAlarmId());
                    alarm.setAlarmType(ReplaceUtils.repManAlarm(newAlarmList.get(i).getAlarmType()));
                    alarm.setDevEUI(deviceService.selectcodeBydeveui(newAlarmList.get(i).getDevEUI()));
                    alarm.setLocation(newAlarmList.get(i).getLocation());
                    alarm.setDevType(tmnType);
                    alarm.setAlarmname(tmnType);
                    alarm.setAlarmContent("温度:" + newAlarmList.get(i).getCurrentAngle() + "℃" +
                            ";背景角度:" + newAlarmList.get(i).getBaseAngle() + "°" +
                            ";电池电压百分比:" + newAlarmList.get(i).getCurrentVoltag() + "%");
                    alarm.setRecoveryTime(newAlarmList.get(i).getRecoveryTime());
                    alarm.setShopId(newAlarmList.get(i).getShopId());
                    alarm.setFirstAlarmTime(newAlarmList.get(i).getFirstAlarmTime());
                    alarm.setShopName(newAlarmList.get(i).getShopName());
                    String coordType = newAlarmList.get(i).getCoordType();
                    if (newAlarmList.get(i).getLatitude() != null) {
                        if (coordType == null || coordType.equals("bd-09")) {
                            gps = PositionUtil.bd09_To_Gps84(newAlarmList.get(i).getLatitude(), newAlarmList.get(i).getLongitude());
                        } else if (coordType.equals("wgs84")) {
                          //  System.out.println(newAlarmList.get(i).getLatitude());
                            gps.setWgLat(DandF.flTodo(newAlarmList.get(i).getLatitude()));
                            gps.setWgLon(DandF.flTodo(newAlarmList.get(i).getLongitude()));
                        } else if (coordType.equals("gcj-02")) {
                            gps = PositionUtil.gcj_To_Gps84(newAlarmList.get(i).getLatitude(), newAlarmList.get(i).getLongitude());
                        }
                        alarm.setLatitude(DandF.pase(gps.getWgLat()));
                        alarm.setLongitude(DandF.pase(gps.getWgLon()));
                    } else {
                        alarm.setLatitude(null);
                        alarm.setLongitude(null);
                    }
                    alarm.setDisposestatus(Integer.parseInt(newAlarmList.get(i).getDisposeStatus()));
                    if (newAlarmList.get(i).getRecoveryStatus() == null) {
                        alarm.setRecoverystatus(1);
                    } else {
                        alarm.setRecoverystatus(Integer.parseInt(newAlarmList.get(i).getRecoveryStatus()));
                    }
                 //   System.out.println(alarm);
                    alarmService.addAlarm(alarm);                           //添加到告警
                if (s != null && id == null) {
                    int c = alarmService.addOrg(alarm.getShopId(), alarm.getAlarmId());         //添加到告警组织

                } else {
                //    System.out.println("此告警数据已存在" + i);
                }
            }
        }

    }

    @PostConstruct
    @Scheduled(cron = "0 0 0/2 * * *")
    //获取用电安全告警信息
    public void getElectricalSafetyAlarm() {
        long endTime = System.currentTimeMillis();
        long startTime = endTime - 1000 * 60 * 60 * 24 * 30* 9;
        Termcontroller termcontroller = new Termcontroller();
        int pageSize = 50;
        int pageNum = 1;
        String tmnType = "electricalSafety";
        for (int m = 0; m < shopId.length; m++) {
            DataResponse<ElectricalSafetyAlarm> dataResponse = termcontroller.getElectricalSafetyAlarm(shopId[m], tmnType, pageNum, pageSize, startTime, endTime);
            List<ElectricalSafetyAlarm> newAlarmList = new ArrayList<>();
            int total = dataResponse.getTotal();
            for (int j = 1; j < total / pageSize + 2; j++) {
                System.out.println("第" + j + "页数据");
                DataResponse<ElectricalSafetyAlarm> data = termcontroller.getElectricalSafetyAlarm(shopId[m], tmnType, j, pageSize, startTime, endTime);
                for (int num = 0; num < data.getList().size(); num++) {
                    newAlarmList.add(data.getList().get(num));
                }
            }
            for (int i = 0; i < newAlarmList.size(); i++) {
                Integer id = alarmService.selectAlarmId(newAlarmList.get(i).getAlarmId());
                String s = deviceService.selectcodeBydeveui(newAlarmList.get(i).getDevEUI()); //判断设备有没有添加

                    Alarm alarm = new Alarm();
                    Gps gps = new Gps();
                    alarm.setAlarmId(newAlarmList.get(i).getAlarmId());
                    alarm.setAlarmType(ReplaceUtils.repEleAlarm(ArrayUtils.toString(newAlarmList.get(i).getAlarmType())));
                    alarm.setDevEUI(deviceService.selectcodeBydeveui(newAlarmList.get(i).getDevEUI()));//查询设备code
                    alarm.setLocation(newAlarmList.get(i).getLocation());
                    alarm.setDevType(tmnType);
                    alarm.setAlarmname(tmnType);
                    alarm.setAlarmContent("实时告警值:" + ((newAlarmList.get(i).getCurrentVal() == null) ? "" : newAlarmList.get(i).getCurrentVal()) + "mA" +
                            ";告警值:" + ((newAlarmList.get(i).getAlarmVal() == null) ? "" : newAlarmList.get(i).getAlarmVal()) + "mA");
                    alarm.setShopId(newAlarmList.get(i).getShopId());
                    alarm.setFirstAlarmTime(newAlarmList.get(i).getFirstAlarmTime());
                    alarm.setShopName(newAlarmList.get(i).getShopName());
                    String coordType = newAlarmList.get(i).getCoordType();
                    if (newAlarmList.get(i).getLatitude() != null) {
                        if (coordType == null || coordType.equals("gcj-02")) {
                            gps = PositionUtil.gcj_To_Gps84(newAlarmList.get(i).getLatitude(), newAlarmList.get(i).getLongitude());
                        } else if (coordType.equals("wgs84")) {
                        //    System.out.println(newAlarmList.get(i).getLatitude());
                            gps.setWgLat(DandF.flTodo(newAlarmList.get(i).getLatitude()));
                            gps.setWgLon(DandF.flTodo(newAlarmList.get(i).getLongitude()));
                        } else if (coordType.equals("bd-09")) {
                            gps = PositionUtil.bd09_To_Gps84(newAlarmList.get(i).getLatitude(), newAlarmList.get(i).getLongitude());
                        }
                        alarm.setLatitude(DandF.pase(gps.getWgLat()));
                        alarm.setLongitude(DandF.pase(gps.getWgLon()));
                    } else {
                        alarm.setLatitude(null);
                        alarm.setLongitude(null);
                    }
                    alarm.setRecoveryTime(newAlarmList.get(i).getRecoveryTime());
                    alarm.setDisposestatus(Integer.parseInt(newAlarmList.get(i).getDisposeStatus()));
                    if (newAlarmList.get(i).getRecoveryStatus() == null) {
                        alarm.setRecoverystatus(1);
                    } else {
                        alarm.setRecoverystatus(Integer.parseInt(newAlarmList.get(i).getRecoveryStatus()));
                    }
                    // System.out.println(alarm);
                    alarmService.addAlarm(alarm);                           //添加到告警
                if (s != null && id == null) {
                    int c = alarmService.addOrg(alarm.getShopId(), alarm.getAlarmId());         //添加到告警组织
                } else {
                    //System.out.println("此告警数据已存在" + i);
                }
            }
        }

    }

}