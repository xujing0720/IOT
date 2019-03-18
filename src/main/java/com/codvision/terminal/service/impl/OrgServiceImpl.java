package com.codvision.terminal.service.impl;

import com.codvision.terminal.bean.Org;
import com.codvision.terminal.dao.OrgMapper;
import com.codvision.terminal.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgServiceImpl implements OrgService {
   @Autowired
   private OrgMapper orgMapper;


    @Override
    public boolean addOrg(Org org) {
        int i=orgMapper.addOrg(org);
        return i>0?true:false;
    }

    @Override
    public List<String> findOrg() {

        return orgMapper.findOrg();
    }
}
