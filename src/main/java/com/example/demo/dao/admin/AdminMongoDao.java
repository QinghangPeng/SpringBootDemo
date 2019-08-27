package com.example.demo.dao.admin;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @ClassName AdminMongoDao
 * @Description
 * @Author jackson
 * @Date 2019/8/27 16:01
 * @Version 1.0
 **/
@Repository
@Slf4j
public class AdminMongoDao {

    @Qualifier("adminMongoTemplate")
    @Autowired
    private MongoTemplate adminTemplate;

    public void createShard(String colName) {
        Document object = new Document();
        object.put("shardCollection","new_report." + colName);
        object.put("key",new Document().append("vin",1).append("tBoxTime",1));
        adminTemplate.executeCommand(object);
    }
}
