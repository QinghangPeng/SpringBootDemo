package com.example.demo.dao;

import com.example.demo.vo.Location;
import com.example.demo.vo.OrgRelation;
import com.example.demo.vo.Qrtzlocks;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MysqlDao {

    List<String> getTriggerName(@Param("triggerGroup") String triggerGroup);

    List<Qrtzlocks> getQrtzLocks();

    List<String> getAllVin(@Param("pageSize") Integer pageSize);

    /**
     *  存储过程写法
     */
    @SuppressWarnings("rawtypes")
    @Select("call pro_ds_getChild(#{rootId})")
    @Options(statementType = StatementType.CALLABLE)
    @Results(value = {
            @Result(property = "id",column = "id"),
            @Result(property = "pid",column = "pid")
    })
    List<OrgRelation> childList(@Param("rootId") int rootId);

    @SuppressWarnings("rawtypes")
    @Select("call pro_ds_getLevel42Child(#{rootId})")
    @Options(statementType = StatementType.CALLABLE)
    @Results(value = {
            @Result(property = "id",column = "id"),
            @Result(property = "pid",column = "pid"),
            @Result(property = "name",column = "name"),
            @Result(property = "level",column = "level")
    })
    List<OrgRelation> getLevel42Child(@Param("rootId") int rootId);

    void updateDealer(@Param("code") String code, @Param("addr") String addr,@Param("bdlongtitude") Double bdlongtitude,
                      @Param("bdlaititude") Double bdlaititude);
}
