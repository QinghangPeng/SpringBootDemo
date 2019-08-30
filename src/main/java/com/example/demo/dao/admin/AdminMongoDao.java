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
        /**
         *  mongo shardkey  主流分为两种：
         *   ranged sharding:  片键组合，会并且只会创建“升序”的索引，散列可能不大，会集中到一个shard上
         *   hashed sharding: 只能一个字段("vin","hashed") 组合，散列范围大，但是查询的时候可能会广播所有shard，速度降低
         */
        object.put("key",new Document().append("vin",1).append("tBoxTime",1));
        Document result = adminTemplate.executeCommand(object);
        log.info("create shard result:{}",result.toJson());
    }
}
