package com.example.demo.redisQueue.producer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName PublisherService
 * @Description  需要用到redis队列的service层可以直接注入该类后进行调用
 * @Author jackson
 * @Date 2018/12/28 16:59
 * @Version 1.0
 **/
@Service
public class PublisherService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public String sendMessage(Map name, String topic) {
        try{
            redisTemplate.convertAndSend(topic,JSONObject.toJSONString(name));
            return "消息发送成功了";
        }catch (Exception e) {
            e.printStackTrace();
            return "消息发送失败了";
        }
    }
}
