package com.codvision.terminal.bean;

import lombok.Data;

@Data
public class Terminal {
//    private String tenantID;//租户 ID
//    private String sceneID;// 场景 ID
//    private String tmnName;// 终端名
//    private String tmnType;// 终端型号名
//    private String firmName;// 厂商名
//    private String firmTopic;// 厂商 Topic
//    private String tmnDevSN;// 终端 devSN
//    private String accessWay;//一级接入方式
//    private String subAccessType;//二级接入方式
//    private String linkType;// 终端接入方式
//    private String indexType;// 终端索引类型
//    private String indexFormat;//终端索引格式，对应终端型号中的 idFormat
//    private String indexLen;// 终端索引长度
//    private String tmnOIDIndex;// 终端唯一标识
//    private String state;// 终端在线状态
//    private String callbackType;// 终端回调类型
//    private String callbackParam;// 终端回调参数
//    private Boolean netConfig;// 网络配置
//    private Boolean isPeripheral;//是否可绑定传感器
//    private String endian;//        终端大小端
//    private String appVersion;//终端版本号
//    private String heartBeat;//        终端心跳间隔
//    private Object  powerSave;//终端省电模式配置
//    private String[] baudrate;//        终端串口波特率配置
//    private String[] dataBits;//    终端串口数据位配置
//    private String[] checkBits;//        终端串口校验位配置
//    private String[] stopBits;//            终端串口停止位配
//    private Object[] property;//终端内置属性
//    private  String propertyTopic;//属性 Topic
//    private String propertyName;//属性名
//    private String interval;//属性采用间隔
//    private String _id;
    private  String lati;
    private String location;
    private String long_;
    private String sceneID;
    private String state;
    private String terminalId;
    private String tmnDevSN;

    @Override
    public String toString() {
        return "Terminal{" +
                "lati='" + lati + '\'' +
                ", location='" + location + '\'' +
                ", long_='" + long_ + '\'' +
                ", sceneID='" + sceneID + '\'' +
                ", state='" + state + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", tmnDevSN='" + tmnDevSN + '\'' +
                ", tmnName='" + tmnName + '\'' +
                ", tmnOIDIndex='" + tmnOIDIndex + '\'' +
                ", tmnType='" + tmnType + '\'' +
                ", alarminfo=" + alarminfo +
                '}';
    }

    private String tmnName;
    private String tmnOIDIndex;
    private String tmnType;


    private Alarminfo alarminfo;

}









