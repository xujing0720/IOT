package com.codvision.terminal.controller;

import com.alibaba.fastjson.JSONObject;
import com.codvision.terminal.common.ResponseEntity;
import com.codvision.terminal.service.DeviceService;
import com.codvision.terminal.util.RequestUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;

@RequestMapping("/iotsmartfirecontrol")
@RestController
//烟感
public class SmartfireController {
    private static final String BASE_URL3 = "https://218.108.146.92:10443/restapi/";
    private static final String BASE_URL4 = "https://218.108.247.143:30443/restapi/";


    @Autowired
    DeviceService deviceService;


    @GetMapping("/getSmartfirecontrol")
    //获取终端列表(烟感)
    public ResponseEntity getSmartfirecontrol(@RequestParam(value = "shopId", required = true) String shopId,
                                              @RequestParam(value = "data", required = true) String data) {
        ResponseEntity responseEntity = new ResponseEntity();
        System.out.println(data + "..........");
        String url = BASE_URL3 + "iot/iotsmartfirecontrol/terminallist";
        String param = "shopId=" + shopId + "&data=" + URLEncoder.encode(data);
        //String param="shopId="+shopId+"&data="+"%7B%22tmnType%22%3A%22TBS-110%22%7D";
        String result = RequestUtil.sendGet(url, param);
        //  System.out.println(result+"...................");
        JSONObject jsonObject = JSONObject.parseObject(result);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            if (result == null) {
                responseEntity.setCode(100);
                responseEntity.setMessage("获取信息错误");
            } else {
                jsonNode = objectMapper.readTree(result);
                JsonNode datajson = jsonNode.findPath("data");
                JsonNode list = datajson.findPath("list");
                System.out.println(list.toString());
 //               List<Terminal> terminalList = JSONArray.parseArray(list.toString(), Terminal.class);
                //存到数据库
//                for (int i = 0; i < terminalList.size(); i++) {
//
//                    Device device = new Device();
//                    device.setCode(terminalList.get(i).getTerminalId());
//                    device.setCreatetime(new Date());
//                    device.setUpdatetime(new Date());
//                    device.setLat(terminalList.get(i).getLati());
//                    device.setLng(terminalList.get(i).getLong_());
//                    device.setType("烟感");
//                    if (terminalList.get(i).getTmnName().equals("TBS-110")) {
//                        device.setManufacturer("武汉拓宝");
//                    } else {
//                        device.setManufacturer("赛特威尔");
//                    }
//
//                    device.setSerialnumber(terminalList.get(i).getTmnDevSN());
//                    device.setModel(terminalList.get(i).getTmnType());
//                    device.setName(terminalList.get(i).getTmnName());
//                    device.setStatus(1);
//                    System.out.println(device.toString());
//                    int j = deviceService.add(device);
//                }

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

    @GetMapping("/getalarmlist")
    //获取实时告警接口
    public ResponseEntity getalarmlist(@RequestParam(value = "shopId", required = true) String shopId) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iotsmartfirecontrol/alarmlist";
        String param = "shopId=" + shopId;
        String result = RequestUtil.sendGet(url, param);
        JSONObject jsonObject = JSONObject.parseObject(result);
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
