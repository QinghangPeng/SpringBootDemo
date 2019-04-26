package com.example.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
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

    @KafkaListener(topics = "test_topic")
    public void getInfo(String record) {
        try{
            logger.info("getInfo success:{}",record);
        } catch(Exception e) {
            logger.error("getInfo error:{}",e);
        }
    }
}
