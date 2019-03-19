package com.example.demo.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MysqlDao {

    List<String> getTriggerName(@Param("triggerGroup") String triggerGroup);

}
