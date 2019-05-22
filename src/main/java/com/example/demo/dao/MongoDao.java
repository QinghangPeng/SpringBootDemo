package com.example.demo.dao;

import com.example.demo.vo.Vehicle;
import com.mongodb.*;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName MongoDao
 * @Description
 * @Author jackson
 * @Date 2019/5/21 15:36
 * @Version 1.0
 **/
@Repository
public class MongoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static final AggregationOptions OPTIONS = AggregationOptions.builder()
            .outputMode(AggregationOptions.OutputMode.CURSOR)
            .allowDiskUse(true)
            .build();

    public List<Vehicle> getVinInfo(List<String> vin) {
        /*Query query = new Query();
        Criteria criteria = Criteria.where("vin").in(vin);
        query.addCriteria(criteria);
        List<Vehicle> list = mongoTemplate.find(query,Vehicle.class,"area_driver_record_city");*/
        /*DBObject query = new BasicDBObject();
        ((BasicDBObject) query).put("vin",new BasicDBObject("$in",vin));*/
        Document query = new Document();
        query.append("vin",new Document().append("$in",vin));
        List<? extends Bson> pipeline = Arrays.asList(
                new Document().append("$match",query)
        );
        MongoCursor<Document> cursor = mongoTemplate.getCollection("area_driver_record_city").aggregate(pipeline).allowDiskUse(false).iterator();
        List<Document> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Document document = cursor.next();
//            list.add(mongoTemplate.getConverter().read(Vehicle.class,document));
            list.add(document);
        }
        System.out.println("================================"+list.size());
        return null;
    }
}
