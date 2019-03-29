package com.codvision.terminal.service;

import com.codvision.terminal.bean.terminals.Terminal;

import java.util.List;

public interface TerminalService {
    int addTerminal(String tenantID, String sceneID, String tmnName, String tmnType, String firmName,
                    String firmTopic, String tmnDevSN, String accessWay, String subAccessType, String linkType,
                    String indexType, String indexFormat, String indexLen, int isPeripheral, String endian,
                    String callbackType, String callbackParam, int netConfig, String heartBeat, Object powerSave,
                    String propertyTopic, String propertyName, String interval);


    List<Terminal> getTerminalList(String sceneID);
}
