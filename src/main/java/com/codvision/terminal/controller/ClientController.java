package com.codvision.terminal.controller;

import com.alibaba.fastjson.JSON;
import com.codvision.terminal.bean.Alarminfo;
import com.codvision.terminal.bean.Device;
import com.codvision.terminal.bean.HeartBeat;
import com.codvision.terminal.bean.SubscribeInfo;
import com.codvision.terminal.common.ResponseEntity;
import com.codvision.terminal.service.AlarmService;
import com.codvision.terminal.service.DeviceService;
import com.codvision.terminal.service.OrgService;
import com.codvision.terminal.util.MapperUtils;
import com.codvision.terminal.util.RequestUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {
    private static final String BASE_URL3="https://218.108.146.92:10443/";
    private static final boolean isRunning = true;
    //  最近的心跳时间
    private long lastHeartbeat;
    // 心跳间隔时间
    private static final long heartBeatInterval = 90 * 1000;
    //心跳标识
    private static final String subId = "";
    //告警信息
    private static final String commonCode = "30001";
    //申请单位编码
    private static final String applicantOrg = "";
    //申请单位名称
    private static final String applicantName = "";

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("YYYYMMddHHmmss");

    @Autowired
    private AlarmService alarmService;
    @Autowired
    private OrgService orgService;

    @Autowired
    private DeviceService deviceService;


    @GetMapping("/getOrg")
    //获取小区信息
    public ResponseEntity getorg() throws Exception {
        ResponseEntity responseEntity = new ResponseEntity();
        String url =BASE_URL3+ "api/org";

        String result = RequestUtil.sendGet(url,null);
        System.out.println(result+"---------");
        if (result == null) {
            responseEntity.setCode(100);
            responseEntity.setMessage("数据接收失败");
            return responseEntity;
        }

       // System.out.println("小区信息： " + result);

//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(result);
//        JsonNode list = jsonNode.findPath("list");
//        List<Org> orglist = MapperUtils.json2list(list.toString(), Org.class);
//        for (int i = 0; i < orglist.size(); i++) {
//            boolean bl = orgService.addOrg(orglist.get(i));
//        }
        responseEntity.setData(result);
        responseEntity.setCode(200);
        responseEntity.setMessage("成功");
        return responseEntity;

    }

    /*
    订阅
     */
    @PostMapping("/postSubscribeInfo")
    public ResponseEntity postSubscribeInfo(@RequestParam(value = "subscribeInfo") SubscribeInfo subscribeInfo) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = "http://IP:port/api/Subscribe";
        List<String> orgcode = orgService.findOrg();
        subscribeInfo.setNowTime(FORMAT.format(new Date()));
        subscribeInfo.setSubTime(FORMAT.format(new Date()));
        subscribeInfo.setOperateType("0");
        subscribeInfo.setSubID(subId);
        subscribeInfo.setReceiveAddr("http://192.168.20.158:8080/VIID/SubscribeNotifications");
        subscribeInfo.setResourceURI("http://IP:port/api/Subscribe");
        subscribeInfo.setApplicantOrg(applicantOrg);
        subscribeInfo.setApplicantName(applicantName);
        subscribeInfo.setSubTime(FORMAT.format(new Date()));
        subscribeInfo.setSubDetail("3000");
        subscribeInfo.setOrgcode(orgcode);
        String s = JSON.toJSONString(subscribeInfo);
        String result = RequestUtil.doPostJson(url,s);

        System.out.println("订阅响应结果：" + result);
        if (result == null) {
            responseEntity.setCode(100);
            responseEntity.setMessage("订阅响应失败");
            return responseEntity;

        }

        responseEntity.setData(result);
        responseEntity.setData(200);
        responseEntity.setMessage("成功");
        return responseEntity;
    }


    @GetMapping("/getAlarm")
    //接收告警数据
    public ResponseEntity addAlarm(@RequestParam(value = "alarm") Alarminfo alarm) throws Exception {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3+"api/alarms";
        String result =RequestUtil.sendGet(url,null);
        System.out.println("告警数据：" + result);
//        if (result == null) {
//            responseEntity.setCode(100);
//            responseEntity.setMessage("告警数据接收失败");
//            return responseEntity;
//        } else {
//            List<Alarminfo> alarmList = MapperUtils.json2list(result, Alarminfo.class);
//            boolean j;
//            for (int i = 0; i < alarmList.size(); i++) {
//               // j = alarmService.addAlarm(alarmList.get(i));
//                if (!j) {
//                    responseEntity.setCode(100);
//                    responseEntity.setMessage("报警数据存储失败");
//                }
//                responseEntity.setCode(200);
//                responseEntity.setMessage("成功");
//            }

            return responseEntity;
        }



    /*
    心跳机制
     */
    @PostMapping("/postHeartBeat")
    public ResponseEntity postHeartBeat() {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3+"api/HeartBeat";
        while (isRunning) {
            long startTime = System.currentTimeMillis();
            if (startTime - lastHeartbeat > heartBeatInterval) {
                lastHeartbeat = startTime;
                HeartBeat heartBeat = new HeartBeat();
                heartBeat.setNowTime(startTime);
                heartBeat.setSubId(subId);
                heartBeat.setCommcode(commonCode);
                heartBeat.setApplicantOrg(applicantOrg);
                String res = JSON.toJSONString(heartBeat);
                CloseableHttpClient httpClient = HttpClients.createDefault();
                String result = RequestUtil.doPostJson(url, res);

                System.out.println("心跳结果：" + result);
                if (result == null) {
                    responseEntity.setCode(100);
                    return responseEntity;
                }
                responseEntity.setCode(200);
                responseEntity.setData(result);
                return responseEntity;

            }

        }

    }

    /*
    查询告警设备
     */
    @GetMapping("/getDevice")
    public ResponseEntity getDevice(@RequestParam(value = "conditionParam")JSON condition) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3+"api/alarmdevice/list?conditionParam="+condition;
        System.out.println(url+"__________________url");
        String result = RequestUtil.sendGet(url,null);
        System.out.println("告警设备：" + result);
        List<Device> list = new ArrayList<>();
        if (result == null) {
            responseEntity.setCode(100);
            return responseEntity;
        }
        try {
            List<String> strings = MapperUtils.json2list(result, "orgcode", String.class);

            for (int i = 0; i < strings.size(); i++) {
                String orgcode = strings.get(i);
                Device device = deviceService.selectDevice(orgcode);
                list.add(device);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        responseEntity.setCode(200);
        responseEntity.setData(list);
        return responseEntity;


    }



}

