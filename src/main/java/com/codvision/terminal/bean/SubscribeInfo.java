package com.codvision.terminal.bean;

import lombok.Data;

import java.util.List;

@Data
//订阅消息
public class SubscribeInfo {
    private String subTime; //订阅时间
    private String nowTime;//当前时间
    private String subID;//订阅标识
    private String subDetail;//订阅类别
    private String receiveAddr;//信息接收地址
    private String applicantOrg;//申请单位编码
    private String applicantName;//申请单位名称
    private String resourceURI;//订阅资源路径
    private String operateType;//操作类型:0-订阅1-取消订阅
    private String cancelTime;//取消订阅时间
    private String updateTime;//更新时间
    private List<String> orgcode;//小区编码
}
