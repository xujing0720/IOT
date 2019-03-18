package com.codvision.terminal.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
//小区信息
@Table(name="tbl_org")
public class Org {
    @Column(name="oid")
    private  String orgcode;//小区编码
    @Column(name = "name")
    private  String orgname;//小区名称
    private Integer type;
    private Integer level;
    private String poid;
    private Integer orderby;
    private String path;
    private String wkt;

}
