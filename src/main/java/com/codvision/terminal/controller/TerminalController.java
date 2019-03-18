package com.codvision.terminal.controller;

import com.alibaba.fastjson.JSONObject;
import com.codvision.terminal.common.ResponseEntity;
import com.codvision.terminal.service.TerminalService;
import com.codvision.terminal.util.RequestUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/iot")
public class TerminalController {
    //外网
    private static final String BASE_URL1 = "https://218.108.146.92:10443/";
    //内网
    private static final String BASE_URL2 = "https://70.100.100.50:10443/";
    private static final String BASE_URL3 = "https://218.108.146.92:10443/restapi/";

    //key
    private static final String KEY = "03a35fea709d454885d6807012595e72";

    @Autowired
    TerminalService terminalService;

    @GetMapping("/getTerminal")
    //获取终端列表
    public ResponseEntity getTerminalList(@RequestParam(value = "sceneID", required = true) String sceneID,
                                          @RequestParam(value = "pageNo", required = true) int pageNo,
                                          @RequestParam(value = "pageSize", required = true) int pageSize) {
        ResponseEntity responseEntity = new ResponseEntity();
        String param = "sceneID=" + sceneID + "&pageNo=" + pageNo + "&pageSize=" + pageSize;
        String url = BASE_URL3 + "iot/iotterminalmgr/getTerminalList";
        String result = RequestUtil.sendGet(url, param);
       // System.out.println("::::"+result);
        JSONObject jsonObject = JSONObject.parseObject(result);
       // System.out.println("+++++"+jsonObject);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            if (result == null || result.equals("")) {
                responseEntity.setCode(100);
                responseEntity.setMessage("暂无数据");
            } else {
                jsonNode = objectMapper.readTree(result);

                JsonNode data = jsonNode.findPath("data");
//                JsonNode list = data.findPath("list");
//
//                if(list==null||list.equals("")){
//                    responseEntity.setCode(jsonNode.findPath("errCode").intValue());
//                    responseEntity.setMessage(jsonNode.findPath("reason").asText());
//                }
//                JsonNode total = data.findPath("total");
//                System.out.println(total);
//                System.out.println(list + "-------");
//               List<Terminal> terminalList = JSONArray.parseArray(list.toString(), Terminal.class);
//                System.out.println(terminalList.size());
//
//                for (int i = 0; i < terminalList.size(); i++) {
//                    System.out.println(terminalList.get(i).getTmnName() + ">>>>>>>>>" + i);
//
//                }
                //System.out.println(result);

                responseEntity.setCode(jsonNode.findPath("errCode").intValue());
                responseEntity.setMessage(jsonNode.findPath("reason").asText());
                responseEntity.setData(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return responseEntity;

    }

    @GetMapping("/getFimname")
    //获取型号列表
    public ResponseEntity getFimname() {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iotfirmmgr/findFirmname";
        String result = RequestUtil.sendGet(url, null);
        JSONObject jsonObject = JSONObject.parseObject(result);
        System.out.println(result);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(result);
            responseEntity.setCode(jsonNode.findPath("errCode").intValue());
            responseEntity.setMessage(jsonNode.findPath("reason").asText());
            responseEntity.setData(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return responseEntity;
    }

    @PostMapping("/addTerminal")
    //添加终端
    public ResponseEntity addTerminal(@RequestParam(value = "tenantID", required = true) String tenantID,
                                      @RequestParam(value = "sceneID", required = true) String sceneID,
                                      @RequestParam(value = "tmnName", required = true) String tmnName,
                                      @RequestParam(value = "tmnType", required = true) String tmnType,
                                      @RequestParam(value = "firmName", required = true) String firmName,
                                      @RequestParam(value = "firmTopic", required = true) String firmTopic,
                                      @RequestParam(value = "tmnDevSN", required = true) String tmnDevSN,
                                      @RequestParam(value = "accessWay", required = true) String accessWay,
                                      @RequestParam(value = "subAccessType", required = false, defaultValue = "") String subAccessType,
                                      @RequestParam(value = "linkType", required = true) String linkType,
                                      @RequestParam(value = "indexType", required = true) String indexType,
                                      @RequestParam(value = "indexFormat", required = true) String indexFormat,
                                      @RequestParam(value = "indexLen", required = true) String indexLen,
                                      @RequestParam(value = "isPeripheral", required = true) Boolean isPeripheral,
                                      @RequestParam(value = "endian", required = true) String endian,
                                      @RequestParam(value = "callbackType ", required = false) String callbackType,
                                      @RequestParam(value = "callbackParam", required = false) String callbackParam,
                                      @RequestParam(value = "netConfig", required = true) Boolean netConfig,
                                      @RequestParam(value = "heartBeat ", required = false, defaultValue = "") String heartBeat,
                                      @RequestParam(value = "powerSave", required = false, defaultValue = "") Object powerSave,
                                      @RequestParam(value = "baudrate", required = false, defaultValue = "[]") String[] baudrate,
                                      @RequestParam(value = "dataBits", required = false, defaultValue = "[]") String[] dataBits,
                                      @RequestParam(value = "checkBits", required = false, defaultValue = "[]") String[] checkBits,
                                      @RequestParam(value = "stopBits", required = false, defaultValue = "[]") String[] stopBits,
                                      @RequestParam(value = "property", required = false, defaultValue = "[]") Object[] property,
                                      @RequestParam(value = "propertyTopic", required = true) String propertyTopic,
                                      @RequestParam(value = "propertyName", required = true) String propertyName,
                                      @RequestParam(value = "interval", required = true) String interval
    ) {

        System.out.println(sceneID + "---" + tenantID);
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iotterminalmgr/addTerminal";
        Map<String, String> params = new HashMap<>();
        params.put("tenantID", tenantID);
        params.put("sceneID", sceneID);
        params.put("tmnName", tmnName);
        params.put("tmnType", tmnType);
        params.put("firmName", firmName);
        params.put("firmTopic", firmTopic);
        params.put("tmnDevSN", tmnDevSN);
        params.put("accessWay", accessWay);
        if (StringUtils.isNotEmpty(heartBeat)) {
            params.put("heartBeat", heartBeat);
        }
        params.put("linkType", linkType);
        params.put("indexType", indexType);
        params.put("indexFormat", indexFormat);
        params.put("indexLen", indexLen);
        params.put("isPeripheral", String.valueOf(isPeripheral));
        params.put("endian", endian);
        params.put("netConfig", String.valueOf(netConfig));
        if (StringUtils.isNotEmpty(subAccessType)) {
            params.put("subAccessType", subAccessType);
        }
        if (StringUtils.isNotEmpty(String.valueOf(powerSave))) {
            params.put("powerSave", String.valueOf(powerSave));
        }
        if (baudrate != null) {
            params.put("baudrate", ArrayUtils.toString(baudrate, ","));
        }
        if (dataBits != null) {
            params.put("dataBits", ArrayUtils.toString(dataBits, ","));

        }
        if (stopBits != null) {
            params.put("stopBits", ArrayUtils.toString(stopBits, ","));

        }
        if (checkBits != null) {
            params.put("checkBits", ArrayUtils.toString(checkBits, ","));
        }
        if (property != null) {
            params.put("property", ArrayUtils.toString(property, ","));
        }

        params.put("propertyTopic", propertyTopic);
        params.put("propertyName", propertyName);
        params.put("interval", interval);
        String result = RequestUtil.doPost(url, params);
        if (result == null) {
            responseEntity.setCode(100);
            responseEntity.setMessage("暂无数据");
        }
        responseEntity.setCode(200);
        responseEntity.setMessage("成功");
        System.out.println(result);
        responseEntity.setData(JSONObject.parseObject(result));
        return responseEntity;
    }


    @PostMapping("/updateTerminal")
    //更新终端
    public ResponseEntity update(@RequestParam(value = "tmnName", required = true) String tmnName,
                                 @RequestParam(value = "tmnOIDIndex", required = true) String tmnOIDIndex,
                                 @RequestParam(value = "callbackType ", required = false, defaultValue = "") String callbackType,
                                 @RequestParam(value = "callbackParam", required = false, defaultValue = "") String callbackParam) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iotterminalmgr/updateTerminal";
        Map<String, String> params = new HashMap<>();
        params.put("tmnName", tmnName);
        params.put("tmnOIDIndex", tmnOIDIndex);
        if (StringUtils.isNotEmpty(callbackParam)) {
            params.put("callbackParam", callbackParam);
        }
        if (StringUtils.isNotEmpty(callbackType)) {
            params.put("callbackType", callbackType);
        }
        String result = RequestUtil.doPost(url, params);
        System.out.println(result + "结果》》》");
        if (result == null) {
            responseEntity.setCode(100);
            responseEntity.setMessage("更新失败");

        }
        responseEntity.setCode(200);
        responseEntity.setMessage("成功");
        responseEntity.setData(JSONObject.parseObject(result));
        return responseEntity;

    }


    @PostMapping("/deleteTerminal")
    //删除终端
    public ResponseEntity deleteTer(@RequestParam(value = "tmnOIDIndex", required = true) String tmnOIDIndex) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iotterminalmgr/delTerminal";
        Map<String, String> params = new HashMap<>();
        params.put("tmnOIDIndex", tmnOIDIndex);
        //删除终端不用firstTopic
        String result = RequestUtil.doPostS(url, params);
        System.out.println(result + "............");
        JSONObject jsonObject = JSONObject.parseObject(result);
//        System.out.println(jsonObject);
        if (result == null || result.equals("")) {
            responseEntity.setCode(100);
            responseEntity.setMessage("删除失败");

        } else {
            responseEntity.setCode(200);
            responseEntity.setMessage("成功");
            responseEntity.setData(jsonObject);
            // System.out.println("dyug");
        }

        return responseEntity;
    }

    @PostMapping("/setConfiguration")
    //下行指令配置
    public ResponseEntity setCfg(@RequestParam(value = "tmnOIDIndex", required = true) String tmnOIDIndex,
                                 @RequestParam(value = "propertyTopic", required = true) String propertyTopic,
                                 @RequestParam(value = "value", required = true) String value) {
        ResponseEntity responseEntity = new ResponseEntity();
        String url = BASE_URL3 + "iot/iottransparent/setConfiguration";
        Map<String, String> params = new HashMap<>();
        params.put("tmnOIDIndex", tmnOIDIndex);
        params.put("propertyTopic", propertyTopic);
        params.put("value", value);
        String result = RequestUtil.doPost(url, params);
        if (result == null || result.equals("")) {
            responseEntity.setCode(100);
            responseEntity.setMessage("删除失败");

        }
        responseEntity.setCode(200);
        responseEntity.setMessage("成功");
        responseEntity.setData(JSONObject.parseObject(result));
        return responseEntity;
    }


}
