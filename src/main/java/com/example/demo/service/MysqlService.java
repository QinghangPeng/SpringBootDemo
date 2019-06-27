package com.example.demo.service;

import com.example.demo.dao.MysqlDao;
import com.example.demo.vo.OrgRelation;
import com.example.demo.vo.Qrtzlocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    public List<OrgRelation> getDataFromStorage(Integer type,Integer rootId) {
        List<OrgRelation> list;
        if (type.intValue() == 1) {
            list = mysqlDao.childList(rootId);
        } else {
            list = mysqlDao.getLevel42Child(rootId);
        }
        return list;
    }

    @Cacheable(value = "qrtzlocks")
    public List<Qrtzlocks> getQrtzLocks() {
        List<Qrtzlocks> list = mysqlDao.getQrtzLocks();
        return list;
    }
}
