package com.codvision.terminal.controller;

import com.alibaba.fastjson.JSONArray;
import com.codvision.terminal.bean.Device;
import com.codvision.terminal.bean.terminals.WaterTerminal;
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
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/iotWaterpressure")
//水压
public class WaterpressureController {
    private static final String BASE_URL3 = "https://218.108.146.92:10443/restapi/";
    @Autowired
    private DeviceService deviceService;

    @GetMapping("/getWaterpressure")
    public ResponseEntity getWaterpressure(@RequestParam(value = "shopId", required = true) String shopId,
                                           @RequestParam(value = "status", required = true) int status,
                                           @RequestParam(value = "tmnIdList", required = false, defaultValue = "") String tmnIdList,
                                           @RequestParam(value = "pageNum", required = true) int pageNum,
                                           @RequestParam(value = "pageSize", required = true) int pageSize) {

        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iot_waterpressure_restapi/external/terminal/realinfo";
        String param = "shopId=" + shopId + "&status=" + status + "&pageSize=" + pageSize + "&pageNum=" + pageNum + "&tmnIdList=" + tmnIdList;
//        BasicNameValuePair pair = new BasicNameValuePair("shopId", shopId);
//        BasicNameValuePair pair1 = new BasicNameValuePair("status", String.valueOf(status));
//        BasicNameValuePair pair2 = new BasicNameValuePair("tmnIdList", tmnIdList);
//        BasicNameValuePair pair3 = new BasicNameValuePair("pageNum", String.valueOf(pageNum));
//        BasicNameValuePair pair4 = new BasicNameValuePair("pageSize", String.valueOf(pageSize));
//        String result = RequestUtil.doGet(url, pair, pair1, pair3, pair4);
        String result = RequestUtil.sendGet(url, param);
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
            JsonNode list = datajson.findPath("dataList");
            List<WaterTerminal> terminalList = JSONArray.parseArray(list.toString(), WaterTerminal.class);
            for (int i = 0; i < terminalList.size(); i++) {
                Device device = new Device();
                device.setCode(terminalList.get(i).getTmnOIDIndex());
                device.setCreatetime(new Date());
                device.setUpdatetime(terminalList.get(i).getReportTime());
                device.setLat(terminalList.get(i).getLat());
                device.setLng(terminalList.get(i).getLng());
                device.setType("水压");
                device.setShopId(shopId);
                device.setManufacturer("杭州拓深科技有限公司");
                device.setSerialnumber(terminalList.get(i).getDevEUI());
                device.setModel(terminalList.get(i).getType());
                device.setName(terminalList.get(i).getTmnName());
                if (terminalList.get(i).getStatus() == 2) {
                    device.setStatus(1);
                }
                else if(terminalList.get(i).getStatus() == 1) {
                    device.setStatus(0);
                }
                else if (terminalList.get(i).getStatus() == 0) {
                    device.setStatus(2);
                }
                System.out.println(device.toString());
                //添加设备
                 deviceService.add(device);

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
