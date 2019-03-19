package com.example.demo.service;

import com.example.demo.dao.MysqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MysqlService
 * @Description
 * @Author jackson
 * @Date 2019/3/19 18:16
 * @Version 1.0
 **/
@Service
public class MysqlService {

    @Autowired
    private MysqlDao mysqlDao;

    public List<String> getTriggerName(String triggerGroup) {
        List<String> list = mysqlDao.getTriggerName(triggerGroup);
        return list;
    }
}
