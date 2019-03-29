package com.codvision.terminal.controller;

import com.alibaba.fastjson.JSONArray;
import com.codvision.terminal.bean.Device;
import com.codvision.terminal.bean.terminals.TerminalEx;
import com.codvision.terminal.bean.alarms.Alarm;
import com.codvision.terminal.bean.alarms.ElectricalSafetyAlarm;
import com.codvision.terminal.bean.alarms.ManholeCoverAlarm;
import com.codvision.terminal.bean.alarms.NewAlarm;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/iotfirewarningns")
//智慧园区
public class SmartParkController {
    private static final String BASE_URL3 = "https://218.108.146.92:10443/restapi/";


    @Autowired
    private DeviceService deviceService;
    @Autowired
    private AlarmService alarmService;

    @GetMapping("/getterminals")
    public ResponseEntity getstwe(@RequestParam(value = "shopId", required = true) String shopId,
                                  @RequestParam(value = "tmnType", required = true) String tmnType,
                                  @RequestParam(value = "devEUI", required = false) String devEUI,
                                  @RequestParam(value = "pageNum", required = true) int pageNum,
                                  @RequestParam(value = "pageSize", required = true) int pageSize) {

        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iotparkdataanalysis/proxy/terminals";
        BasicNameValuePair pair = new BasicNameValuePair("shopId", shopId);
        BasicNameValuePair pair1 = new BasicNameValuePair("tmnType", tmnType);
        BasicNameValuePair pair2 = new BasicNameValuePair("devEUI", devEUI);
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
                Device device = new Device();
                device.setCode(terminalList.get(i).getOidIndex());
                device.setCreatetime(new Date());
                device.setUpdatetime(terminalList.get(i).getUpdateTime());
                device.setLat(terminalList.get(i).getLatitude());
                device.setLng(terminalList.get(i).getLongitude());
                device.setType(tmnType);
                device.setShopId(terminalList.get(i).getShopId());
                device.setManufacturer(terminalList.get(i).getTmnFacturer());
                device.setSerialnumber(terminalList.get(i).getDevEUI());
                device.setModel(terminalList.get(i).getDevType());
                device.setName(terminalList.get(i).getTmnName());
                device.setStatus(Integer.parseInt(terminalList.get(i).getOnlineStatus()));
                System.out.println(device.toString());
                //添加设备
                // deviceService.add(device);
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

    //获取告警列表
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
        String result = RequestUtil.doGet(url, pair, pair1, pair3, pair4);
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
                    System.out.println(alarm);
                    //添加
                    // alarmService.addAlarm(alarm);

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
                    alarm.setShopId(newAlarmList.get(i).getShopId());
                    alarm.setFirstAlarmTime(newAlarmList.get(i).getFirstAlarmTime());
                    alarm.setShopName(newAlarmList.get(i).getShopName());
                    alarm.setLatitude(newAlarmList.get(i).getLatitude());
                    alarm.setLongitude(newAlarmList.get(i).getLongitude());
                    System.out.println(alarm);
                    //  alarmService.addAlarm(alarm);

                }
            }
            //用电安全
            if (tmnType.equals("electricalSafety")) {
                List<ElectricalSafetyAlarm> newAlarmList = JSONArray.parseArray(list.toString(), ElectricalSafetyAlarm.class);
                for (int i = 0; i < newAlarmList.size(); i++) {
                    Alarm alarm = new Alarm();
                    alarm.setAlarmId(newAlarmList.get(i).getAlarmId());
                    alarm.setAlarmType(ReplaceUtils.repEleAlarm(ArrayUtils.toString(newAlarmList.get(i).getAlarmType())));
                    alarm.setDevEUI(deviceService.selectcodeBydeveui(newAlarmList.get(i).getDevEUI()));
                    alarm.setLocation(newAlarmList.get(i).getLocation());
                    alarm.setDevType(tmnType);
                    alarm.setAlarmname(tmnType);
                    alarm.setShopId(newAlarmList.get(i).getShopId());
                    alarm.setFirstAlarmTime(newAlarmList.get(i).getFirstAlarmTime());
                    alarm.setShopName(newAlarmList.get(i).getShopName());
                    alarm.setLatitude(newAlarmList.get(i).getLatitude());
                    alarm.setLongitude(newAlarmList.get(i).getLongitude());
                    System.out.println(alarm);
                    // alarmService.addAlarm(alarm);
                   
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

}

