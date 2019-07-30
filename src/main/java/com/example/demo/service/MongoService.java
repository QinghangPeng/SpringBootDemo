package com.example.demo.service;

import com.example.demo.dao.MongoDao;
import com.example.demo.dao.MysqlDao;
import com.example.demo.vo.Vehicle;
import com.example.demo.vo.mongoVo.IndexReq;
import com.example.demo.vo.mongoVo.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MongoService
 * @Description
 * @Author jackson
 * @Date 2019/5/21 15:31
 * @Version 1.0
 **/
@Service
@Slf4j
public class MongoService {

    @Autowired
    private MysqlDao mysqlDao;

    @Autowired
    private MongoDao mongoDao;

    public List<Vehicle> basicMongoInfo(Integer pagesize) {
        Long start = System.currentTimeMillis();
        log.info("开始执行：{}",start);
        List<String> vins = mysqlDao.getAllVin(pagesize);
        List<Vehicle> list = mongoDao.getVinInfo(vins);
        log.info("执行完成:{}",(System.currentTimeMillis() - start)/1000);
        return list;
    }

    public void createColWithIndex(IndexReq indexReq) {
        mongoDao.createColWithIndex(indexReq.getColName(),indexReq.getList());
    }

    public void createColWithObje(Weather weather) {
        mongoDao.createColWithObje(weather);
    }
}
