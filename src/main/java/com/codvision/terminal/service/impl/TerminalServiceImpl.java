package com.codvision.terminal.service.impl;

import com.codvision.terminal.bean.Terminal;
import com.codvision.terminal.dao.TerminnalMapper;
import com.codvision.terminal.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminalServiceImpl implements TerminalService {
    @Autowired
    TerminnalMapper terminnalMapper;

    @Override
    public int addTerminal(String tenantID, String sceneID, String tmnName, String tmnType, String firmName, String firmTopic, String tmnDevSN, String accessWay, String subAccessType, String linkType, String indexType, String indexFormat, String indexLen, int isPeripheral, String endian, String callbackType, String callbackParam, int netConfig, String heartBeat, Object powerSave, String propertyTopic, String propertyName, String interval) {
        return terminnalMapper.addTerminal(tenantID,sceneID,tmnName,tmnType,firmName,firmTopic,tmnDevSN,accessWay,subAccessType,
                linkType,indexType,indexFormat,indexLen,isPeripheral,endian,callbackType,callbackParam,netConfig,heartBeat,powerSave,
                propertyTopic,propertyName,interval);
    }

    @Override
    public List<Terminal> getTerminalList(String sceneID) {
        return terminnalMapper.getTerminalList(sceneID);
    }
}
