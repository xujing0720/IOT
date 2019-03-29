package com.codvision.terminal.dao;

import com.codvision.terminal.bean.terminals.Terminal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TerminnalMapper {
    @Select("select * from tbl_terminal where sceneID=#{sceneID}")
    List<Terminal> getTerminalList(String sceneID);

    int addTerminal(String tenantID, String sceneID, String tmnName, String tmnType, String firmName,
                    String firmTopic, String tmnDevSN, String accessWay, String subAccessType, String linkType,
                    String indexType, String indexFormat, String indexLen, int isPeripheral, String endian,
                    String callbackType, String callbackParam, int netConfig, String heartBeat, Object powerSave,
                    String propertyTopic, String propertyName, String interval);
}
