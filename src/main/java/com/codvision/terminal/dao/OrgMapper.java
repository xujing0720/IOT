package com.codvision.terminal.dao;

import com.codvision.terminal.bean.Org;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrgMapper {

    @Insert("insert into tbl_org (oid,name) values(#{orgcode},#{orgname})")
    int addOrg(Org org);

    @Select("select oid orgcode from tbl_org")
    List<String> findOrg();
}
