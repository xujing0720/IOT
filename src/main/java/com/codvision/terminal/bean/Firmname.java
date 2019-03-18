package com.codvision.terminal.bean;

import lombok.Data;

@Data
public class Firmname {
    private String firmTopic;//厂商 Topic
    private String firmTopicName;//厂商 Topic 名
    private String firmTopicType;//    厂商 Topic 类型
    private String firmName;//    厂商名
    private String tenantID;//    用户名
    private String terminalType;//    终端型号名
    private String idType;//    终端型号标识类型
    private String idLen;//    终端型号标识长度
    private String idFormat;//    终端型号标识格式
    private String accessWay;//    一级接入方式
    private String subAccessType;//    二级接入方式
    private String accessType;//    接入方式
    private  Boolean  isPeripheral;//    终端型号是否可绑定传感器
    private String endia;//    终端型号大小
    private  Object  powerSave;//省电模式
    private String heartbeat;//        心跳间隔
    private Integer[] baudrate;//支持波特率数组
    private Integer[] dataBits;//        持数据位数组
    private String[]  stopBits;//支持停止位数组
    private String[]  checkBit;//        支持校验位数


}
