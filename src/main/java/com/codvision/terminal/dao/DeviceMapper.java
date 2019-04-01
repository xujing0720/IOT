package com.codvision.terminal.dao;

import com.codvision.terminal.bean.Device;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DeviceMapper {

    @Select("SELECT d.code,d.name,d.type,d.lng,d.lat,d.status,d.orgcode,o.name orgname FROM tbl_device d " +
            "JOIN tbl_org o on o.oid=d.orgcode WHERE o.oid=#{orgcode}")
    Device selectDevice(@Param("orgcode") String orgcode);

    @Insert("INSERT INTO tbl_device (code,type,name,model,manufacturer,serial_number,lng,lat,status,create_time,update_time,org_code,devicetor,devicetor_mobile) " +
            "VALUES(#{code},#{type},#{name},#{model},#{manufacturer},#{serialnumber},#{lng},#{lat},#{status},#{createtime},#{updatetime},#{shopId},#{devicetor},#{devicetorMobile})")
    int add(Device device);

    @Select("SELECT code FROM tbl_device where serial_number=#{devEUI}")
    String selectcode(@Param("devEUI")String devEUI);
}
