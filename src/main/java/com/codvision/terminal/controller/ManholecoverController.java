package com.codvision.terminal.controller;

import com.alibaba.fastjson.JSONObject;
import com.codvision.terminal.common.ResponseEntity;
import com.codvision.terminal.util.RequestUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/iot_manholecover")
@RestController
//井盖监测终端
public class ManholecoverController {
    private static final String BASE_URL3 = "https://218.108.146.92:10443/restapi/";
    @PostMapping("/getmanholecoverrealinfo")
    // 根据 devEUIList 获取所有对应井盖的事实信息
    public ResponseEntity getmanholecoverrealinfo(@RequestParam(value = "devEUIList",required = true)String[] devEUIList ) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iot_manholecover/getmanholecoverrealinfo";
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
}
