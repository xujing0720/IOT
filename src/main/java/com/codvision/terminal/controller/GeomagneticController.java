package com.codvision.terminal.controller;

import com.alibaba.fastjson.JSONObject;
import com.codvision.terminal.common.ResponseEntity;
import com.codvision.terminal.util.RequestUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/iotgeomagnetic")
//地磁终端
public class GeomagneticController {
    private static final String BASE_URL3 = "https://218.108.146.92:10443/restapi/";
    @GetMapping("/getrealinfo")
   //获取指定场景下所有的地磁传感器的实时
    public ResponseEntity getrealin(@RequestParam(value = "scenarioId",required = true)String scenarioId){
        ResponseEntity responseEntity=new ResponseEntity();
        String url=BASE_URL3+"iot/iot_geomagnetic/getrealinfo";
        BasicNameValuePair pair = new BasicNameValuePair("scenarioId", scenarioId);
        String result = RequestUtil.doGet(url, pair);
        System.out.println(result);
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

    @GetMapping("/getparkinglotcount")
    //获取指定场景下车位使用情况
    public ResponseEntity getparkinglotcount(@RequestParam(value = "scenarioId",required = true)String scenarioId){
        ResponseEntity responseEntity=new ResponseEntity();
        String url=BASE_URL3+"iot/iot_geomagnetic/getparkinglotcount";
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

    @PostMapping("/getrealgeomagneticinfo")
    //获取车位集合中所有车位的实时
    public ResponseEntity getrealgeomagneticin(@RequestParam(value = "devEUIList",required = true)String[] devEUIList ) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iot_geomagnetic/getrealgeomagneticinfo";
        Map<String,String> map=new HashMap<>();
        map.put("devEUIList", String.valueOf(devEUIList));
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

    @GetMapping("/getonerealgeomagneticinfo")
    //获取单个车位的实时停车信息
    public ResponseEntity getonerealgeomagneticinfo (@RequestParam(value = "oid",required = true)String oid){
        ResponseEntity responseEntity=new ResponseEntity();
        String url=BASE_URL3+"iot/iot_geomagnetic/getonerealgeomagneticinfo";
        BasicNameValuePair pair = new BasicNameValuePair("oid", oid);
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

    @GetMapping("/getonehistgeomagneticinfo")
    //取单个车位状态为在位的历史信息
    public ResponseEntity getonehistgeomagneticinfo (@RequestParam(value = "oid",required = true)String oid){
        ResponseEntity responseEntity=new ResponseEntity();
        String url=BASE_URL3+"iot/iot_geomagnetic/getonehistgeomagneticinfo";
        BasicNameValuePair pair = new BasicNameValuePair("oid", oid);
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

    @PostMapping("/gethistgeomagneticlastinfo")
    //获取传感器集合中所有地磁终端状态为在位的历史数据
    public ResponseEntity gethistgeomagneticlastinfo(@RequestParam(value = "devEUIList",required = true)String[] devEUIList ) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iot_geomagnetic/gethistgeomagneticlastinfo";
        Map<String,String> map=new HashMap<>();
        map.put("devEUIList", String.valueOf(devEUIList));
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

    @PostMapping("/gethistgeomagneticinfo")
    //获取传感器集合在某个时间点上在位状态的车位历史数据
    public ResponseEntity gethistgeomagneticinfo(@RequestParam(value = "devEUIList",required = true)String[] devEUIList,
                                                 @RequestParam(value = "time",required = true)Number time) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iot_geomagnetic/gethistgeomagneticlastinfo";
        Map<String,String> map=new HashMap<>();
        map.put("devEUIList", String.valueOf(devEUIList));
        map.put("time", String.valueOf(time));
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

}
