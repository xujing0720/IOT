package com.codvision.terminal.controller;

import com.alibaba.fastjson.JSONObject;
import com.codvision.terminal.common.ResponseEntity;
import com.codvision.terminal.util.RequestUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/iot_environment")
//智慧城市环境终端
public class EnvironmentController {
    private static final String BASE_URL3 = "https://218.108.146.92:10443/restapi/";

    @GetMapping("/getTerminal")
    public ResponseEntity getTerminal(@RequestParam(value = "shopId", required = true) String shopId,
                                      @RequestParam(value = "pageSize", required = true) Number pageSize,
                                      @RequestParam(value = "pageNum", required = true) Number pageNum,
                                      @RequestParam(value = "keyword", required = false) String keyword) {
        ResponseEntity responseEntity = new ResponseEntity();
        String param = null;
//        if(keyword==null||keyword.equals("")){
//            param="shopId="+shopId+"&pageSize="+pageSize+"&pageNum="+pageNum;
//        }
        param = "shopId=" + shopId + "&pageSize=" + pageSize + "&pageNum=" + pageNum + "&keyword=" + keyword;
        String url = BASE_URL3 + "iot/iot_environment/terminallist";
        BasicNameValuePair pair = new BasicNameValuePair("shopId", shopId);
        BasicNameValuePair pair1 = new BasicNameValuePair("pageSize", String.valueOf(pageSize));
        BasicNameValuePair pair2 = new BasicNameValuePair("pageNum", String.valueOf(pageNum));
        BasicNameValuePair pair3 = new BasicNameValuePair("keyword", keyword);
        String result = RequestUtil.doGet(url, pair, pair1, pair2, pair3);


        //String result = RequestUtil.sendGet(url, param);

        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            if (result == null) {
                responseEntity.setCode(100);
                responseEntity.setMessage("获取信息错误");
            }
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

    @GetMapping("/getAttribute")
    public ResponseEntity getAttibute(@RequestParam(value = "shopId", required = true) String shopId,
                                      @RequestParam(value = "devEUI", required = true) String devEUI) {
        ResponseEntity responseEntity = new ResponseEntity();
        String param = "shopId=" + shopId + "&devEUI=" + devEUI;
        String url = BASE_URL3 + "iot/iot_environment/terminallist";
        String result = RequestUtil.sendGet(url, param);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            if (result == null) {
                responseEntity.setCode(100);
                responseEntity.setMessage("获取信息错误");
            }
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

    @GetMapping("/getAttribute24")
   // 获取某个传感器下某个属性近 24 小时的历史信息
    public ResponseEntity getAttribute24(@RequestParam(value = "shopId",required = true)String shopId,
                                         @RequestParam(value = "devEUI",required = true)String devEUI,
                                         @RequestParam(value = "subAddr",required = true)String subAddr,
                                         @RequestParam(value = "portId",required = true)String portId,
                                         @RequestParam(value = "type",required = true)String type){
        ResponseEntity responseEntity=new ResponseEntity();
        String url=BASE_URL3+"iot/iot_environment/attributehistinfo";
        BasicNameValuePair pair = new BasicNameValuePair("shopId", shopId);
        BasicNameValuePair pair1 = new BasicNameValuePair("devEUI", devEUI);
        BasicNameValuePair pair2 = new BasicNameValuePair("subAddr", subAddr);
        BasicNameValuePair pair3 = new BasicNameValuePair("portId", portId);
        BasicNameValuePair pair4 = new BasicNameValuePair("type", type);
        String result = RequestUtil.doGet(url, pair, pair1, pair2, pair3, pair4);
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
