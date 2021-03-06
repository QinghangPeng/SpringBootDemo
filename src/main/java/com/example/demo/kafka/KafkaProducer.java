package com.example.demo.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName KafkaProducer
 * @Description
 * @Author jackson
 * @Date 2019/4/26 17:31
 * @Version 1.0
 **/
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String,String> producer;

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    public void send(String topic,Object object) {
        try{
            Map map = new HashMap<>();
            map.put(KafkaHeaders.TOPIC,topic);
//            map.put(KafkaHeaders.PARTITION_ID,4);
            map.put(KafkaHeaders.TIMESTAMP,System.currentTimeMillis());

            producer.send(new GenericMessage<>(objectMapper.writeValueAsString(object),map));
        } catch(Exception e) {
            logger.error("send error:{}",e);
        }
    }
}
