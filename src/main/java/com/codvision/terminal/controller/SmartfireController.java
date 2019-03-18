package com.codvision.terminal.controller;

import com.alibaba.fastjson.JSONObject;
import com.codvision.terminal.common.ResponseEntity;
import com.codvision.terminal.util.RequestUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/iotsmartfirecontrol")
@RestController
public class SmartfireController {
    private static final String BASE_URL3 = "https://218.108.146.92:10443/restapi/";
    @GetMapping("/getSmartfirecontrol")
    //获取终端列表
    public ResponseEntity getSmartfirecontrol(@RequestParam(value = "shopId",required = true)String shopId,
                                              @RequestParam(value = "data",required = true)Object data){
        ResponseEntity responseEntity=new ResponseEntity();
        String url=BASE_URL3+"iot/iotsmartfirecontrol";
        String param="shopId="+shopId+"&data="+data;
        String result = RequestUtil.sendGet(url, param);
        JSONObject jsonObject = JSONObject.parseObject(result);
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

    @GetMapping("/getalarmlist")
    //获取实时告警接口
    public ResponseEntity getalarmlist(@RequestParam(value = "shopId",required = true)String shopId){
        ResponseEntity responseEntity=new ResponseEntity();
        String url=BASE_URL3+"iot/iotsmartfirecontrol/alarmlist";
        String param="shopId="+shopId;
        String result = RequestUtil.sendGet(url, param);
        JSONObject jsonObject = JSONObject.parseObject(result);
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
