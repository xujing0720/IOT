package com.codvision.terminal.controller;

import com.alibaba.fastjson.JSONArray;
import com.codvision.terminal.bean.alarms.ElectricalSafetyAlarm;
import com.codvision.terminal.bean.alarms.ManholeCoverAlarm;
import com.codvision.terminal.bean.alarms.NewAlarm;
import com.codvision.terminal.bean.terminals.TerminalEx;
import com.codvision.terminal.common.DataResponse;
import com.codvision.terminal.service.AlarmService;
import com.codvision.terminal.service.DeviceService;
import com.codvision.terminal.util.RequestUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
@Component
public class Termcontroller {
    private static final String BASE_URL3 = "https://218.108.146.92:10443/restapi/";

    @Autowired
    DeviceService deviceService;
    @Autowired
   AlarmService alarmService;
    //获取设备
    public DataResponse getstwe(String shopId, String tmnType, int pageNum, int pageSize) {
        DataResponse responseEntity = new DataResponse();
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
        List<TerminalEx> terminalExList=null;
        if (result == null) {
            responseEntity.setCode(100);
            responseEntity.setMessage("获取信息错误");
        }
        try {
            jsonNode = objectMapper.readTree(result);
            JsonNode datajson = jsonNode.findPath("data");

            JsonNode list = datajson.findPath("list");
            terminalExList = JSONArray.parseArray(list.toString(), TerminalEx.class);
            JsonNode code = jsonNode.findPath("code");
            JsonNode total=datajson.findPath("total");
            JsonNode message = jsonNode.findPath("message");
            responseEntity.setCode(code.intValue());
            responseEntity.setMessage(message.asText());
            responseEntity.setList(terminalExList);
            responseEntity.setTotal(total.intValue());


        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    //获取烟感告警
    public DataResponse<NewAlarm> getSmokerAlarmslist(String shopId, String tmnType, int pageNum, int pageSize) {
        DataResponse responseEntity = new DataResponse();
        String url = BASE_URL3 + "iot/iotparkdataanalysis/proxy/alarms";
        BasicNameValuePair pair = new BasicNameValuePair("shopId", shopId);
        BasicNameValuePair pair1 = new BasicNameValuePair("tmnType", tmnType);
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
            JsonNode total=datajson.findPath("total");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<NewAlarm> newAlarmList = JSONArray.parseArray(list.toString(), NewAlarm.class);
            responseEntity.setList(newAlarmList);
            responseEntity.setTotal(total.intValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }


    //获取井盖告警
    public DataResponse<ManholeCoverAlarm> getmanholeCoverAlarmslist(String shopId, String tmnType, int pageNum, int pageSize) {
        DataResponse responseEntity = new DataResponse();
        String url = BASE_URL3 + "iot/iotparkdataanalysis/proxy/alarms";
        BasicNameValuePair pair = new BasicNameValuePair("shopId", shopId);
        BasicNameValuePair pair1 = new BasicNameValuePair("tmnType", tmnType);
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
            JsonNode total=datajson.findPath("total");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<ManholeCoverAlarm> newAlarmList = JSONArray.parseArray(list.toString(), ManholeCoverAlarm.class);
            responseEntity.setList(newAlarmList);
            responseEntity.setTotal(total.intValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    //获取用电安全告警
    public DataResponse<ElectricalSafetyAlarm> getElectricalSafetyAlarm(String shopId, String tmnType, int pageNum, int pageSize) {
        DataResponse responseEntity = new DataResponse();
        String url = BASE_URL3 + "iot/iotparkdataanalysis/proxy/alarms";
        BasicNameValuePair pair = new BasicNameValuePair("shopId", shopId);
        BasicNameValuePair pair1 = new BasicNameValuePair("tmnType", tmnType);
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
            JsonNode total=datajson.findPath("total");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<ElectricalSafetyAlarm> newAlarmList = JSONArray.parseArray(list.toString(), ElectricalSafetyAlarm.class);
            responseEntity.setList(newAlarmList);
            responseEntity.setTotal(total.intValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

}