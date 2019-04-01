package com.codvision.terminal.bean.user;

import lombok.Data;

import java.util.List;

/**
 * 用户信息表
 */

@Data
public class User {
    private String uid;//uid主键
    private String loginName;//登录名
    private String password;//登录密码
    private String name;//姓名
    private String mobile;//手机号码
    private String remark;//备注
    private int status;//状态
    private String createTime;//创建时间
    private float lng;//经度
    private float lat;//纬度
    private String onlineTime;//在线时间
    private String oid;//组织id
    private String roleName;//角色名称
    private String oName;//组织名称
    private String path;//组织路径
    private int roleId;//角色id
    private UserOrgRole activeOrgRole;//组织id
    private List<UserOrgRole> orgRoles;//  角色列表


    public String getActiveOid() {
        if (null == activeOrgRole) {
            return null;
        }

        return activeOrgRole.getOid();
    }

    public int getActiveRoleId() {
        if (null == activeOrgRole) {
            return -1;
        }

        return activeOrgRole.getRoleId();
    }

}
