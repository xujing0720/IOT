package com.codvision.terminal.util;

import java.util.HashMap;
import java.util.Map;
//字符串替换工具类
public class ReplaceUtils {

    /**
    *烟感告警替换
    */
    public static String replist(String str) {
        Map<String, String> map = new HashMap<String, String>() {
            {
                put("muteAlarm", "多重告警");
                put("fra", "火灾告警");
                put("sfa", "故障告警");
                put("lba", "低电量告警");
                put("soa", "防拆告警");
                put("voltage", "低电压告警");
                put("angle", "倾斜异动告警");
                put("offlineAlarm", "离线告警");
            }
        };
        for (Map.Entry<String, String> entry : map.entrySet()) {
            str = str.replace(entry.getKey(), entry.getValue());
        }
        return str;
    }
    /*
    *用电安全告警替换
    */
    public static String repEleAlarm(String str) {
        Map<String, String> map = new HashMap<String, String>() {
            {
                put("0x01", "剩余电量告警");
                put("0x06", "电弧告警");
                put("0x02","故障告警");
                put("0x03","故障告警");
                put("0x04","火灾告警");
                put("0x05","故障告警");
                put("temperature", "温度告警");
                put("fault", "故障告警");
                put("0x07", "离线告警");
            }
        };
        for (Map.Entry<String, String> entry : map.entrySet()) {
            str = str.replace(entry.getKey(), entry.getValue());
        }
        return str;
    }
    /**
     * 井盖告警替换
     */
    public static String repManAlarm(String str) {
        Map<String, String> map = new HashMap<String, String>() {
            {
                put("voltage", "低电压告警");
                put("angle", "倾斜异动告警");
                put("offline", "离线告警");
            }
        };
        for (Map.Entry<String, String> entry : map.entrySet()) {
            str = str.replace(entry.getKey(), entry.getValue());
        }
        return str;
    }

}
