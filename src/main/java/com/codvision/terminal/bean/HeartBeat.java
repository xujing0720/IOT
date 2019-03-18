package com.codvision.terminal.bean;

import lombok.Data;

@Data
//心跳信息
public class HeartBeat {
    private String commcode;//心跳命令码
    private Long  nowTime;//当前时间
    private String subId;//订阅标识
    private String applicantOrg;//申请单位编码
    private String applicantName;//申请单位名称

}
