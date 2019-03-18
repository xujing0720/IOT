package com.codvision.terminal.controller;

import com.alibaba.fastjson.JSONObject;
import com.codvision.terminal.bean.TrashCan;
import com.codvision.terminal.common.ResponseEntity;
import com.codvision.terminal.util.RequestUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/iot_trashcan")
@RestController
public class TrashcanController {
    private static final String BASE_URL3 = "https://218.108.146.92:10443/restapi/";
    @PostMapping("/gettrashcanrealinfo")
    //根据 devEUIList 获取所有对应垃圾桶的事实信息
    public ResponseEntity gettrashcanrealinfo(@RequestParam(value = "oidList",required = true)String[] oidList ) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iot_trashcan/gettrashcanrealinfo";
        Map<String,String> map=new HashMap<>();
        map.put("devEUIList", String.valueOf(oidList));
        String result = RequestUtil.doPost(url, map);
        JSONObject jsonObject = JSONObject.parseObject(result);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(result);
            JsonNode data = jsonNode.findPath("data");
            JsonNode code = jsonNode.findPath("code");
            JsonNode message = jsonNode.findPath("message");

            responseEntity.setCode(code.intValue());
            responseEntity.setMessage(message.asText());
            responseEntity.setData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    @PostMapping("/getalarmdeveuilist")
    //获取正在告警的垃圾桶的终端唯一标识符集合
    public ResponseEntity getalarmdeveuilist(@RequestParam(value = "oidList",required = true)String[] oidList ) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iot_trashcan/getalarmdeveuilist";
        Map<String,String> map=new HashMap<>();
        map.put("devEUIList", String.valueOf(oidList));
        String result = RequestUtil.doPost(url, map);
        JSONObject jsonObject = JSONObject.parseObject(result);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(result);
            JsonNode data = jsonNode.findPath("data");
            JsonNode code = jsonNode.findPath("code");
            JsonNode message = jsonNode.findPath("message");

            responseEntity.setCode(code.intValue());
            responseEntity.setMessage(message.asText());
            responseEntity.setData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }


    @PostMapping("/gettrashcanalarmcount")
    //获取一天或者一周的垃圾桶的告警次数
    public ResponseEntity gettrashcanalarmcount(@RequestParam(value = "trashCanList",required = true) TrashCan[] trashCanLis,
                                                @RequestParam(value = "type",required = true)String type) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iot_trashcan/gettrashcanalarmcount";
        Map<String,String> map=new HashMap<>();
        map.put("devEUIList", String.valueOf(trashCanLis));
        map.put("type",type);
        String result = RequestUtil.doPost(url, map);
        JSONObject jsonObject = JSONObject.parseObject(result);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(result);
            JsonNode data = jsonNode.findPath("data");
            JsonNode code = jsonNode.findPath("code");
            JsonNode message = jsonNode.findPath("message");

            responseEntity.setCode(code.intValue());
            responseEntity.setMessage(message.asText());
            responseEntity.setData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping("/getlasttrashcanalarminfo")
    // 获取最新的告警信息
    public ResponseEntity getlasttrashcanalarminfo(@RequestParam(value = "scenarioId",required = true) String scenarioId) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iot_trashcan/getlasttrashcanalarminfo";
        BasicNameValuePair pair = new BasicNameValuePair("scenarioId", scenarioId);
        String result = RequestUtil.doGet(url, pair);
        JSONObject jsonObject = JSONObject.parseObject(result);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(result);
            JsonNode data = jsonNode.findPath("data");
            JsonNode code = jsonNode.findPath("code");
            JsonNode message = jsonNode.findPath("message");

            responseEntity.setCode(code.intValue());
            responseEntity.setMessage(message.asText());
            responseEntity.setData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping("/getlastalarminfo")
    // 按场景获取了垃圾桶的最新告警信息
    public ResponseEntity getlastalarminfo(@RequestParam(value = "scenarioId",required = true) String scenarioId) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iot_trashcan/getlastalarminfo";
        BasicNameValuePair pair = new BasicNameValuePair("scenarioId", scenarioId);
        String result = RequestUtil.doGet(url, pair);
        JSONObject jsonObject = JSONObject.parseObject(result);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(result);
            JsonNode data = jsonNode.findPath("data");
            JsonNode code = jsonNode.findPath("code");
            JsonNode message = jsonNode.findPath("message");

            responseEntity.setCode(code.intValue());
            responseEntity.setMessage(message.asText());
            responseEntity.setData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }

    @GetMapping("/gettrashcancount")
    // 按场景获取实时的告警和正常的垃圾桶的数量
    public ResponseEntity gettrashcancount(@RequestParam(value = "scenarioId",required = true) String scenarioId) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iot_trashcan/gettrashcancount";
        BasicNameValuePair pair = new BasicNameValuePair("scenarioId", scenarioId);
        String result = RequestUtil.doGet(url, pair);
        JSONObject jsonObject = JSONObject.parseObject(result);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(result);
            JsonNode data = jsonNode.findPath("data");
            JsonNode code = jsonNode.findPath("code");
            JsonNode message = jsonNode.findPath("message");

            responseEntity.setCode(code.intValue());
            responseEntity.setMessage(message.asText());
            responseEntity.setData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }
}
