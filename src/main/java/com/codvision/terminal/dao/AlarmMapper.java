package com.codvision.terminal.dao;

import com.codvision.terminal.bean.alarms.Alarm;
import com.codvision.terminal.bean.Alarminfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AlarmMapper {
/*
 "alarm_id" int4 NOT NULL DEFAULT nextval('tbl_alarm_alarm_id_seq'::regclass),
  "alarm_code" varchar(64) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "alarm_name" varchar(64) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "alarm_level" varchar(8) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "alarm_position" varchar(128) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "target_code" varchar(64) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "target_type" varchar(8) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "alarm_time" timestamp(6) DEFAULT NULL,
  "create_time" timestamp(6) DEFAULT now(),
 */


    @Insert("insert into tbl_alarm (alarm_code,alarm_name,alarm_type,alarm_position,target_code,target_type,alarm_time,org_code,org_name,lat,lng,disposestatus,recoverystatus) " +
            "values(#{alarmId},#{alarmname},#{alarmType},#{location},#{devEUI},#{devType},#{firstAlarmTime},#{shopId},#{shopName},#{latitude},#{longitude},#{disposestatus},#{recoverystatus})")
    int addAlarm(Alarm alarm);


    @Select("select * from tbl_alarm")
    @Results(id = "alarmResult",value = {
            @Result(property = "alarmcode",column = "alarm_code"),
            @Result(property = "info1",column = "alarm_name"),
            @Result(property = "level",column = "alarm_level"),
            @Result(property = "place",column = "alarm_position"),
            @Result(property = "devicecode",column = "target_code"),
            @Result(property = "targettype",column = "target_type"),
            @Result(property = "alarmtime",column = "alarm_time")
    })
    List<Alarminfo> getAlarm();

    @Update("update tbl_alarm set lng=#{longitude},lat=#{latitude} where alarm_code=#{alarmId}")
    int updateAlarmLg(Alarm alarm);

    @Update("update tbl_alarm set recovery_time=#{recoveryTime} where alarm_code=#{alarmId}")
    int updateAlarmStatus(Alarm alarm);

    @Select("select alarm_id from tbl_alarm where alarm_code=#{alarmId}")
    Integer selectAlarmId(@Param("alarmId") String alarmId);

    @Insert("INSERT INTO tbl_alarm_org_mapper \n" +
            "SELECT o.oid,alarm_id FROM tbl_alarm d INNER JOIN tbl_org o on d.org_code=o.ref_oid and d.org_code=#{shopId} and d.alarm_code=#{alarmId}")
    int addOrg(@Param("shopId")String shopId,@Param("alarmId") String alarmId);
}
