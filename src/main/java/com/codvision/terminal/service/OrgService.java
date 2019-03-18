package com.codvision.terminal.service;

import com.codvision.terminal.bean.Org;

import java.util.List;

public interface OrgService {


    boolean addOrg(Org org);

    List<String> findOrg();
}
