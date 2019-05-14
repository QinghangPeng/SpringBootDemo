package com.example.demo.dao;

import com.example.demo.vo.Qrtzlocks;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MysqlDao {

    List<String> getTriggerName(@Param("triggerGroup") String triggerGroup);

    List<Qrtzlocks> getQrtzLocks();

}
