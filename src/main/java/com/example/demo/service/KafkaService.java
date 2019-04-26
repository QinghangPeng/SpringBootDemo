package com.example.demo.service;

import com.example.demo.kafka.KafkaProducer;
import com.example.demo.vo.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName KafkaService
 * @Description
 * @Author jackson
 * @Date 2019/4/26 17:42
 * @Version 1.0
 **/
@Service
public class KafkaService {

    @Autowired
    private KafkaProducer producer;

    public void sendVehInfo(String topic, Vehicle vehicle) {
        producer.send(topic,vehicle);
    }

}
