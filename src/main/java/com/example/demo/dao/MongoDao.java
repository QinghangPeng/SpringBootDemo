package com.example.demo.dao;

import com.example.demo.vo.Vehicle;
import com.example.demo.vo.mongoVo.Weather;
import com.mongodb.*;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @ClassName MongoDao
 * @Description
 * @Author jackson
 * @Date 2019/5/21 15:36
 * @Version 1.0
 **/
@Repository
@Slf4j
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
        log.info("================================:{}",list.size());
        return null;
    }

    /**
     *  聚合查询简版 查询的原生语句为
     *  {
     *      "$match":{
     *           vin：vin,
     *           time:{
     *               "$gte":fromTime,
     *               "$lte":toTime
     *           }
     *          "$and":[
     *              {"$or":[
     *                  {on:1},{off:1},{up:1},{down:1}
     *              ]}
     *          ]
     *      }
     *  }
     * @param vin
     * @param fromTime
     * @param toTime
     * @return
     */
    public List<Map> searchInfoUseAgg(String vin,Long fromTime,Long toTime) {
        Criteria criteria = Criteria.where("vin").is(vin)
                .and("time").gte(fromTime).lte(toTime);

        List<Criteria> orCriterias = new ArrayList<>();

        /** or查询条件 */
        String[] events = {"on","off","up","down"};
        List<String> list = new ArrayList<>(Arrays.asList(events));
        /** or查询条件 */

        list.forEach(s -> orCriterias.add(Criteria.where(s).is(1)));

        Criteria eleQuery = new Criteria();
        eleQuery.orOperator(orCriterias.toArray(new Criteria[]{}));

        criteria.andOperator(eleQuery);

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.project(new String[]{"time","name","on","up","off","down"}),
                Aggregation.sort(Sort.Direction.ASC,"time")
        );

        AggregationResults<Map> aggResults = mongoTemplate.aggregate(agg,"status_data",Map.class);
        return aggResults.getMappedResults();
    }

    /**
     *   springboot 2.x 版本  mongo聚合查询写法
     *   以及 match 下面 同时包含 "$and" "$or" 的写法
     *   在此做一个记录
     * @param from
     * @param to
     * @param vin
     * @return
     */
    public List<Document> calCount(Long from,Long to,String vin) {
        List<? extends Bson> pipeline = Arrays.asList(
                new Document()
                        .append("$match", new Document()
                                .append("$and", Arrays.asList(
                                        new Document()
                                                .append("tBoxTime", new Document()
                                                        .append("$gte", from)
                                                        .append("$lte", to)
                                                )
                                        )
                                )
                                .append("$or", Arrays.asList(
                                        new Document()
                                                .append("vin", vin)
                                                .append("vehicleOverspeedAlarmSignal", 1),
                                        new Document()
                                                .append("vin", vin)
                                                .append("engineOverspeedAlarmSignal", 1),
                                        new Document()
                                                .append("vin", vin)
                                                .append("rapidDecelerationAlarmSignal", 1),
                                        new Document()
                                                .append("vin", vin)
                                                .append("coastingEndAlarmSignal", 1)
                                        )
                                )
                        ),
                new Document()
                        .append("$group", new Document()
                                .append("_id", 0)
                                .append("overSpeedCount", new Document()
                                        .append("$sum", "$vehicleOverspeedAlarmSignal")
                                )
                                .append("overEngineCount", new Document()
                                        .append("$sum", "$engineOverspeedAlarmSignal")
                                )
                                .append("decelerationCount", new Document()
                                        .append("$sum", "$rapidDecelerationAlarmSignal")
                                )
                                .append("coastingCount", new Document()
                                        .append("$sum", "$coastingEndAlarmSignal")
                                )
                        ),
                new Document()
                        .append("$project", new Document()
                                .append("_id", 0)
                                .append("overSpeedCount", "$overSpeedCount")
                                .append("overEngineCount", "$overEngineCount")
                                .append("decelerationCount", "$decelerationCount")
                                .append("coastingCount", "$coastingCount")
                        )
        );
        AggregateIterable iterable = mongoTemplate.getCollection("event_data").aggregate(pipeline);
        List<Document> list = new ArrayList<>();
        iterable.forEach((Block<Document>) document -> {
            list.add(document);
            //格式化为对应实体的写法 记录
//           Xx xx = mongoTemplate.getConverter().read(Xx.class,document);
        });
        return list;
    }

    /**
     *  创建带索引的表
     * @param colName
     * @param strs 这里可以根据需求创建单索引，单个复合索引，多个复合索引，缺乏:创建多个单索引
     */
    public void createColWithIndex(String colName,List<String[]> strs) {
        if (!mongoTemplate.collectionExists(colName)) {
            for (String[] str : strs) {
                Index index = ensureIndex(str);
                mongoTemplate.indexOps(colName).ensureIndex(index);
            }
        }
    }

    /**
     *  创建索引
     * @param fieldName
     * @return
     */
    private Index ensureIndex(String... fieldName) {
        Index index = null;
        if (fieldName.length == 1) {
            /**
             *  单字段单索引
             */
            index = new Index(fieldName[0],Sort.Direction.ASC);
        } else {
            /**
             *  复合索引
             */
            Document document = new Document();
            for (String s : fieldName) {
                document.put(s,1);
            }
            index = new CompoundIndexDefinition(document);
        }
        return index;
    }

    public void createColWithObje(Weather weather) {
        if (!mongoTemplate.collectionExists("weather")) {
            mongoTemplate.createCollection(Weather.class);
        }
        mongoTemplate.insert(weather);
    }

    /**
     *  springboot 1.x 版本  mongo聚合查询写法
     *  以及 match 下面 同时包含 "$and" "$or" 的写法
     *  在此做一个记录
     */
    /*public BasicDBObject calCount(Long from,Long to,String vin) {
        List<DBObject> pipeline = Arrays.asList(
                new BasicDBObject()
                        .append("$match", new BasicDBObject()
                                .append("$and", Arrays.asList(
                                        new BasicDBObject()
                                                .append("tBoxTime", new BasicDBObject()
                                                        .append("$gte", from)
                                                        .append("$lte", to)
                                                )
                                        )
                                )
                                .append("$or", Arrays.asList(
                                        new BasicDBObject()
                                                .append("vin", vin)
                                                .append("vehicleOverspeedAlarmSignal", 1),
                                        new BasicDBObject()
                                                .append("vin", vin)
                                                .append("engineOverspeedAlarmSignal", 1),
                                        new BasicDBObject()
                                                .append("vin", vin)
                                                .append("rapidDecelerationAlarmSignal", 1),
                                        new BasicDBObject()
                                                .append("vin", vin)
                                                .append("coastingEndAlarmSignal", 1)
                                        )
                                )
                        ),
                new BasicDBObject()
                        .append("$group", new BasicDBObject()
                                .append("_id", 0)
                                .append("overSpeedCount", new BasicDBObject()
                                        .append("$sum", "$vehicleOverspeedAlarmSignal")
                                )
                                .append("overEngineCount", new BasicDBObject()
                                        .append("$sum", "$engineOverspeedAlarmSignal")
                                )
                                .append("decelerationCount", new BasicDBObject()
                                        .append("$sum", "$rapidDecelerationAlarmSignal")
                                )
                                .append("coastingCount", new BasicDBObject()
                                        .append("$sum", "$coastingEndAlarmSignal")
                                )
                        ),
                new BasicDBObject()
                        .append("$project", new BasicDBObject()
                                .append("_id", 0)
                                .append("overSpeedCount", "$overSpeedCount")
                                .append("overEngineCount", "$overEngineCount")
                                .append("decelerationCount", "$decelerationCount")
                                .append("coastingCount", "$coastingCount")
                        )
        );
        Cursor cursor = mongoTemplate.getCollection("event_data").aggregate(pipeline, OPTIONS);
        if (cursor.hasNext()) {
            BasicDBObject document = (BasicDBObject) cursor.next();
            return document;
        }
        return null;
    }*/

}
