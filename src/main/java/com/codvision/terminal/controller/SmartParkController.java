package com.codvision.terminal.controller;

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
@RequestMapping("/iotfirewarningns")
//智慧园区
public class SmartParkController {
    private static final String BASE_URL3 = "https://218.108.146.92:10443/restapi/";
    @GetMapping("/getterminals")
    public ResponseEntity getstwe(@RequestParam(value = "shopId",required = true)String shopId,
                                  @RequestParam(value = "tmnType",required = true)String tmnType,
                                  @RequestParam(value = "devEUI",required = false)String devEUI,
                                  @RequestParam(value = "pageNum",required = true)int pageNum,
                                  @RequestParam(value = "pageSize",required = true)int pageSize){

        ResponseEntity responseEntity = new ResponseEntity();
        String url=BASE_URL3+"iot/iotparkdataanalysis/proxy/terminals";

        BasicNameValuePair pair=new BasicNameValuePair("shopId",shopId);
        BasicNameValuePair pair1=new BasicNameValuePair("tmnType",tmnType);
        BasicNameValuePair pair2=new BasicNameValuePair("devEUI",devEUI);
        BasicNameValuePair pair3=new BasicNameValuePair("pageNum",String.valueOf(pageNum));
        BasicNameValuePair pair4=new BasicNameValuePair("pageSize",String.valueOf(pageNum));
        String result = RequestUtil.doGet(url, pair,pair1,pair3,pair4);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }
}
