package com.codvision.terminal.bean.user;

import lombok.Data;

@Data
public class UserOrgRole {
    private String uid;
    private String oid;
    private int roleId;
    private int orderby;
    private boolean active;
  //  private Org org;
    //private Role role;
}
