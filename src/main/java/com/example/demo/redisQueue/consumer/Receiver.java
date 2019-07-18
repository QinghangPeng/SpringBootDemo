package com.example.demo.redisQueue.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassName Receiver
 * @Description
 * @Author jackson
 * @Date 2018/12/28 17:13
 * @Version 1.0
 **/
@Component
public class Receiver{

    private static Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void getOpenTboxInfo(String message) {
        logger.info("进入了opentboxinfo方法");
        /*RedisSerializer<String> valueSerializer = redisTemplate.getStringSerializer();
        String deserizlize = valueSerializer.deserialize(message.getBody());
        logger.info("收到的消息：{}",deserizlize);
        realVehicleConditionHandler.sendMessageToAllUsers(new TextMessage(deserizlize));*/
    }

    public void getVehicleInfo(String message) {
        logger.info("进入了VehicleInfo方法");
        /*RedisSerializer<String> valueSerializer = redisTemplate.getStringSerializer();
        String deserizlize = valueSerializer.deserialize(message.getBody());
        logger.info("收到的消息：{}",deserizlize);
        realVehicleConditionHandler.sendMessageToAllUsers(new TextMessage(deserizlize));*/
    }


}
