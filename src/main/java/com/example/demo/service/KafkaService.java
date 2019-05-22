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

    public void sendVehInfo(String topic, Vehicle vehicle) throws Exception{
        int i = 1;
        String vin = "ph_tboxId";
        while (true) {
            vehicle.setVin(vin + i%5);
            vehicle.setTboxId(vin+i);
            producer.send(topic,vehicle);
            i++;
//            Thread.sleep(3000L);
        }
    }

}
