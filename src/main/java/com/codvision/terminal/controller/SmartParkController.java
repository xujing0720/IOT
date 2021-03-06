package com.codvision.terminal.controller;

import com.alibaba.fastjson.JSONArray;
import com.codvision.terminal.bean.alarms.Alarm;
import com.codvision.terminal.bean.alarms.ElectricalSafetyAlarm;
import com.codvision.terminal.bean.alarms.ManholeCoverAlarm;
import com.codvision.terminal.bean.alarms.NewAlarm;
import com.codvision.terminal.bean.devices.Device;
import com.codvision.terminal.bean.terminals.TerminalEx;
import com.codvision.terminal.bean.zuobiao.Gps;
import com.codvision.terminal.common.ResponseEntity;
import com.codvision.terminal.service.AlarmService;
import com.codvision.terminal.service.DeviceService;
import com.codvision.terminal.util.ReplaceUtils;
import com.codvision.terminal.util.RequestUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/iotfirewarningns")
//智慧园区
public class SmartParkController {
    private static final String BASE_URL3 = "https://218.108.146.92:10443/restapi/";


    @Autowired
    DeviceService deviceService;
    @Autowired
    AlarmService alarmService;

    @GetMapping("/getterminals")
    public ResponseEntity getstwe(@RequestParam(value = "shopId", required = true) String shopId,
                                  @RequestParam(value = "tmnType", required = true) String tmnType,
//                                  @RequestParam(value = "devEUI", required = false,defaultValue = "") String devEUI,
                                  @RequestParam(value = "pageNum", required = true) int pageNum,
                                  @RequestParam(value = "pageSize", required = true) int pageSize
    ) {

        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iotparkdataanalysis/proxy/terminals";
        BasicNameValuePair pair = new BasicNameValuePair("shopId", shopId);
        BasicNameValuePair pair1 = new BasicNameValuePair("tmnType", tmnType);
        BasicNameValuePair pair2 = new BasicNameValuePair("devEUI", "");
        BasicNameValuePair pair3 = new BasicNameValuePair("pageNum", String.valueOf(pageNum));
        BasicNameValuePair pair4 = new BasicNameValuePair("pageSize", String.valueOf(pageSize));


        String result = RequestUtil.doGet(url, pair, pair1, pair3, pair4);
        System.out.println(result);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        if (result == null) {
            responseEntity.setCode(100);
            responseEntity.setMessage("获取信息错误");
        }
        try {
            jsonNode = objectMapper.readTree(result);
            JsonNode datajson = jsonNode.findPath("data");

            JsonNode list = datajson.findPath("list");
//            System.out.println(list.toString());

//        存到数据库
            List<TerminalEx> terminalList = JSONArray.parseArray(list.toString(), TerminalEx.class);
            for (int i = 0; i < terminalList.size(); i++) {
                Gps gps = null;
                // System.out.println(terminalList.get(i).getDevEUI());
                String devEUI = terminalList.get(i).getDevEUI();
                //   System.out.println(deviceService.selectcodeBydeveui(devEUI)+"...............");
                Device device = new Device();
                device.setCode(terminalList.get(i).getOidIndex());
                device.setCreatetime(new Date());
                device.setUpdatetime(terminalList.get(i).getUpdateTime());
                String coordType = terminalList.get(i).getCoordType();
//                System.out.println(coordType);
//                if (terminalList.get(i).getLatitude() != null) {
//                    if (coordType == null || coordType.equals("wgs84")) {
//                        gps.setWgLat(terminalList.get(i).getLatitude());
//                        gps.setWgLon(terminalList.get(i).getLongitude());
//                    }
//                    if (coordType.equals("bd-09")) {
//                        gps = PositionUtil.bd09_To_Gps84(terminalList.get(i).getLatitude(), terminalList.get(i).getLongitude());
//                    }
//                    if (coordType.equals("gcj-02")) {
//                        gps = PositionUtil.gcj_To_Gps84(terminalList.get(i).getLatitude(), terminalList.get(i).getLongitude());
//                    }
//                }
//                device.setLat(gps.getWgLat());
//                device.setLng(gps.getWgLon());
                device.setLat(terminalList.get(i).getLatitude());
                device.setLng(terminalList.get(i).getLongitude());
                device.setType(tmnType);
                device.setShopId(terminalList.get(i).getShopId());
                device.setManufacturer(terminalList.get(i).getTmnFacturer());
                device.setSerialnumber(terminalList.get(i).getDevEUI());
                device.setModel(terminalList.get(i).getDevType());
                device.setName(terminalList.get(i).getTmnName());
                device.setStatus(Integer.parseInt(terminalList.get(i).getOnlineStatus()));
                //添加设备
                // deviceService.add(device);
                System.out.println(device);
                int o = deviceService.updateLg(device.getLat(), device.getLng());
            }
            JsonNode code = jsonNode.findPath("code");
            JsonNode total = datajson.findPath("total");
            JsonNode message = jsonNode.findPath("message");
            responseEntity.setCode(code.intValue());
            responseEntity.setMessage(message.asText());
            responseEntity.setData(datajson);
            responseEntity.setTotal(total.intValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    //根据设备 sn(列表)获取终端业务数据最新一条报文信息
    @GetMapping("/getalaarm")
    public ResponseEntity getalarm(@RequestParam(value = "shopId", required = true) String shopId,
                                   @RequestParam(value = "tmnType", required = true) String tmnType,
                                   @RequestParam(value = "devEUI", required = false) String devEUI,
                                   @RequestParam(value = "pageNum", required = true) int pageNum,
                                   @RequestParam(value = "pageSize", required = true) int pageSize) {

        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iotparkdataanalysis/proxy/getlastpacketinfos";

        BasicNameValuePair pair = new BasicNameValuePair("shopId", shopId);
        BasicNameValuePair pair1 = new BasicNameValuePair("tmnType", tmnType);
        BasicNameValuePair pair2 = new BasicNameValuePair("devEUI", devEUI);
        BasicNameValuePair pair3 = new BasicNameValuePair("pageNum", String.valueOf(pageNum));
        BasicNameValuePair pair4 = new BasicNameValuePair("pageSize", String.valueOf(pageSize));
        String result = RequestUtil.doGet(url, pair, pair1, pair3, pair4);
        System.out.println(result);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            if (result == null) {
                responseEntity.setCode(100);
                responseEntity.setMessage("获取信息错误");
            }
            jsonNode = objectMapper.readTree(result);
            JsonNode datajson = jsonNode.findPath("data");

            JsonNode list = datajson.findPath("list");
            System.out.println(list.toString());

            JsonNode code = jsonNode.findPath("code");
            JsonNode message = jsonNode.findPath("message");
            responseEntity.setCode(code.intValue());
            responseEntity.setMessage(message.asText());
            responseEntity.setData(datajson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }


    /**
     * @Description:  获取告警列表
     * @Param:
     * @return:
     * @Author: XJ
     * @Date: 2019/4/8
     */
    @GetMapping("/getAlarmslist")
    public ResponseEntity getAlarmslist(@RequestParam(value = "shopId", required = true) String shopId,
                                        @RequestParam(value = "tmnType", required = true) String tmnType,
                                        @RequestParam(value = "query", required = false) String query,
                                        @RequestParam(value = "recoveryStatus", required = false) String recoveryStatus,
                                        @RequestParam(value = "disposeStatus", required = false) String disposeStatus,
                                        @RequestParam(value = "alarmType", required = false) String alarmType,
                                        @RequestParam(value = "startTime", required = false) Long startTime,
                                        @RequestParam(value = "endTime", required = false) Long endTime,
                                        @RequestParam(value = "pageNum", required = true) int pageNum,
                                        @RequestParam(value = "pageSize", required = true) int pageSize) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iotparkdataanalysis/proxy/alarms";
        BasicNameValuePair pair = new BasicNameValuePair("shopId", shopId);
        BasicNameValuePair pair1 = new BasicNameValuePair("tmnType", tmnType);
        //BasicNameValuePair pair2 = new BasicNameValuePair("devEUI", devEUI);
        BasicNameValuePair pair3 = new BasicNameValuePair("pageNum", String.valueOf(pageNum));
        BasicNameValuePair pair4 = new BasicNameValuePair("pageSize", String.valueOf(pageSize));
        BasicNameValuePair pair5 = new BasicNameValuePair("startTime", String.valueOf(startTime));
        BasicNameValuePair pair2 =new BasicNameValuePair("endTime", String.valueOf(endTime));
        String result = RequestUtil.doGet(url, pair, pair1, pair3, pair4,pair2,pair5);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            if (result == null) {
                responseEntity.setCode(100);
                responseEntity.setMessage("获取信息错误");
            }
            jsonNode = objectMapper.readTree(result);
            JsonNode datajson = jsonNode.findPath("data");
            JsonNode list = datajson.findPath("list");
            System.out.println(list.toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //烟感
            if (tmnType.equals("smokeDetector")) {
                List<NewAlarm> newAlarmList = JSONArray.parseArray(list.toString(), NewAlarm.class);
                for (int i = 0; i < newAlarmList.size(); i++) {
                    Alarm alarm = new Alarm();
                    alarm.setAlarmId(newAlarmList.get(i).getAlarmId());
                    alarm.setAlarmType(ReplaceUtils.replist(String.join(",", newAlarmList.get(i).getAlarmType())));
                    alarm.setDevEUI(deviceService.selectcodeBydeveui(newAlarmList.get(i).getDevEUI()));
                    alarm.setLocation(newAlarmList.get(i).getLocation());
                    alarm.setDevType(tmnType);
                    alarm.setAlarmname(tmnType);
                    alarm.setShopId(newAlarmList.get(i).getShopId());
                    alarm.setFirstAlarmTime(newAlarmList.get(i).getFirstAlarmTime());
                    alarm.setShopName(newAlarmList.get(i).getShopName());
                    alarm.setLatitude(newAlarmList.get(i).getLatitude());
                    alarm.setLongitude(newAlarmList.get(i).getLongitude());
                    alarm.setDisposestatus(Integer.parseInt(newAlarmList.get(i).getDisposeStatus()));
                    alarm.setAlarmContent("温度：" + newAlarmList.get(i).getTemperature() + "℃" +
                            ";电池电压百分比:" + newAlarmList.get(i).getBatteryVoltage() + "%" +
                            ";烟雾浓度百分比:" + newAlarmList.get(i).getSmokeScope() + "%");
                    if (newAlarmList.get(i).getRecoveryStatus() == null) {
                        alarm.setRecoverystatus(1);
                    } else {
                        alarm.setRecoverystatus(Integer.parseInt(newAlarmList.get(i).getRecoveryStatus()));
                    }
                    alarm.setRecoveryTime(newAlarmList.get(i).getRecoveryTime());
                    System.out.println(alarm);
                    //添加
                    // alarmService.addAlarm(alarm);
                    alarmService.updateAlarmStatus(alarm);
                }
            }
//            List<ManholeCoverAlarm> newAlarmList = JSONArray.parseArray(list.toString(), ManholeCoverAlarm.class);

            //井盖
            if (tmnType.equals("manholeCover")) {
                List<ManholeCoverAlarm> newAlarmList = JSONArray.parseArray(list.toString(), ManholeCoverAlarm.class);
                for (int i = 0; i < newAlarmList.size(); i++) {
                    Alarm alarm = new Alarm();
                    alarm.setAlarmId(newAlarmList.get(i).getAlarmId());
                    alarm.setAlarmType(ReplaceUtils.repManAlarm(ArrayUtils.toString(",", newAlarmList.get(i).getAlarmType())));
                    alarm.setDevEUI(deviceService.selectcodeBydeveui(newAlarmList.get(i).getDevEUI()));
                    alarm.setLocation(newAlarmList.get(i).getLocation());
                    alarm.setDevType(tmnType);
                    alarm.setAlarmname(tmnType);
                    alarm.setAlarmContent("温度:" + newAlarmList.get(i).getCurrentAngle() + "℃" +
                            ";背景角度:" + newAlarmList.get(i).getBaseAngle() + "°" +
                            ";电池电压百分比:" + newAlarmList.get(i).getCurrentVoltag() + "%");
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
                    alarm.setRecoveryTime(newAlarmList.get(i).getRecoveryTime());
                    System.out.println(alarm);
                    //添加
                    // alarmService.addAlarm(alarm);
                    alarmService.updateAlarmStatus(alarm);
                }
            }
            //用电安全
            if (tmnType.equals("electricalSafety")) {
                List<ElectricalSafetyAlarm> newAlarmList = JSONArray.parseArray(list.toString(), ElectricalSafetyAlarm.class);
                for (int i = 0; i < newAlarmList.size(); i++) {
                    Alarm alarm = new Alarm();
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
                    alarm.setLatitude(newAlarmList.get(i).getLatitude());
                    alarm.setLongitude(newAlarmList.get(i).getLongitude());
                    alarm.setDisposestatus(Integer.parseInt(newAlarmList.get(i).getDisposeStatus()));
                    if (newAlarmList.get(i).getRecoveryStatus() == null) {
                        alarm.setRecoverystatus(1);
                    } else {
                        alarm.setRecoverystatus(Integer.parseInt(newAlarmList.get(i).getRecoveryStatus()));
                    }
                    alarm.setRecoveryTime(newAlarmList.get(i).getRecoveryTime());
                    System.out.println(alarm);
                    //添加
                    //alarmService.addAlarm(alarm);
                    alarmService.updateAlarmStatus(alarm);
                }
            }
            //可燃性气体
            if (tmnType.equals("combustibleGas")) {

            }
            //燃气表
            if (tmnType.equals("gasmeter")) {
//                List<NewAlarm> newAlarmList = JSONArray.parseArray(list.toString(), NewAlarm.class);
//                for (int i = 0; i < newAlarmList.size(); i++) {
//                    Alarm alarm = new Alarm();
//                    alarm.setAlarmId(newAlarmList.get(i).getAlarmId());
//                    alarm.setAlarmType(ArrayUtils.toString(newAlarmList.get(i).getAlarmType()));
//                    alarm.setDevEUI(newAlarmList.get(i).getDevEUI());
//                    alarm.setLocation(newAlarmList.get(i).getLocation());
//                    alarm.setDevType(tmnType);
//                    alarm.setAlarmname("燃气表");
//                    alarm.setShopId(newAlarmList.get(i).getShopId());
//                    alarm.setFirstAlarmTime(newAlarmList.get(i).getFirstAlarmTime());
//                    alarm.setShopName(newAlarmList.get(i).getShopName());
//                alarm.setLatitude(newAlarmList.get(i).getLatitude());
//                alarm.setLongitude(newAlarmList.get(i).getLongitude());
//                    System.out.println(alarm);
//                    // alarmService.addAlarm(alarm);
//                }
            }


            JsonNode code = jsonNode.findPath("code");
            JsonNode message = jsonNode.findPath("message");
            responseEntity.setCode(code.intValue());
            responseEntity.setMessage(message.asText());
            responseEntity.setData(datajson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    /**
    * @Description: 水表
    * @Param:
    * @return:
    * @Author: XJ
    * @Date: 2019/4/8
    */


    @GetMapping("/gethydro")
    public ResponseEntity gethydro(@RequestParam(value = "scenarioId")String scenarioId,
                                   @RequestParam(value = "pageSize")int pageSize,
                                   @RequestParam(value = "pageNum")int pageNum,
                                   @RequestParam(value = "getType")String getType,
                                   @RequestParam(value = "data")String data){
        ResponseEntity responseEntity=new ResponseEntity();
        String data1="{"+"\"scenarioId\":"+"\""+scenarioId+"\""+","+"\"getType\":"+"\""+getType+"\""+",\"pageSize\":"+pageSize+",\"pageNum\":"+pageNum+"}";
        System.out.println(data1);
        System.out.println(data);
        String url=BASE_URL3+"iot/iothydroelectricas/gettmndetails?data="+ URLEncoder.encode(data1);
        String result = RequestUtil.sendGet(url, null);
        //JSONObject jsonObject = JSONObject.parseObject(result);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            if (result == null) {
                responseEntity.setCode(100);
                responseEntity.setMessage("获取信息错误");
            } else {
                jsonNode = objectMapper.readTree(result);
                JsonNode datajson = jsonNode.findPath("data");
                JsonNode list = datajson.findPath("terminals");
                System.out.println(list.toString());
                JsonNode code = jsonNode.findPath("code");
                JsonNode message = jsonNode.findPath("message");

                responseEntity.setCode(code.intValue());
                responseEntity.setMessage(message.asText());
                responseEntity.setData(datajson);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseEntity;


    }
}

