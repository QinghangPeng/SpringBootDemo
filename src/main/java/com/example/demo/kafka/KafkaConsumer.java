package com.example.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @ClassName KafkaConsumer
 * @Description
 * @Author jackson
 * @Date 2019/4/26 17:31
 * @Version 1.0
 **/
@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(id = "partion1",groupId = "consumer1",/*topicPartitions = {@TopicPartition(topic = "test_topic",partitions = {"2","3"})}*/topics = "test_topic")
    public void getInfo1(@Payload String record,
                         /*@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,*/
                         @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        try{
            logger.info("partion1 receive : \n"+
                    "data : "+record+"\n"+
                    "partitionId : "+partition+"\n"+
                    "topic : "+topic+"\n"+
                    "timestamp : "+ts+"\n");
        } catch(Exception e) {
            logger.error("getInfo error:{}",e);
        }
    }

    @KafkaListener(id = "partion2",groupId = "consumer1",/*topicPartitions = {@TopicPartition(topic = "test_topic",partitions = {"0","1","4"})}*/topics = "test_topic")
    public void getInfo2(@Payload String record,
                         /*@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,*/
                         @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        try{
            logger.info("partion2 receive : \n"+
                    "data : "+record+"\n"+
                    "partitionId : "+partition+"\n"+
                    "topic : "+topic+"\n"+
                    "timestamp : "+ts+"\n");
        } catch(Exception e) {
            logger.error("getInfo error:{}",e);
        }
    }

    @KafkaListener(id = "partion3",topics = "test_consumerconfig_topic",containerFactory = "testListenerContainerFactory")
    public void testConsumerConfig(@Payload String record,
            /*@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,*/
                                   @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                                   @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                   @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        try{
            logger.info("partion3 receive : \n"+
                    "data : "+record+"\n"+
                    "partitionId : "+partition+"\n"+
                    "topic : "+topic+"\n"+
                    "timestamp : "+ts+"\n");
        } catch(Exception e) {
            logger.error("getInfo error:{}",e);
        }
    }
}
