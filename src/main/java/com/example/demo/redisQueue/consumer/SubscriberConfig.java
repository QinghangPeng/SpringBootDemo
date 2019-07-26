package com.example.demo.redisQueue.consumer;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @ClassName SubscriberConfig
 * @Description
 * @Author jackson
 * @Date 2018/12/28 17:17
 * @Version 1.0
 **/
@Configuration
@AutoConfigureAfter({Receiver.class})
public class SubscriberConfig {

    /**
     * 消息监听适配器，注入接受消息方法，输入方法名字，反射方法
     * @param receiver
     * @return
     */
    @Bean
    public MessageListenerAdapter openTboxMessageListenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver,"getOpenTboxInfo");
    }

    @Bean
    public MessageListenerAdapter vehicleInfoMessageListenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver,"getVehicleInfo");
    }

    @Bean
    public RedisMessageListenerContainer getRedisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
                                                                          MessageListenerAdapter openTboxMessageListenerAdapter,
                                                                          MessageListenerAdapter vehicleInfoMessageListenerAdapter) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(openTboxMessageListenerAdapter,new PatternTopic("topic_name"));
        redisMessageListenerContainer.addMessageListener(vehicleInfoMessageListenerAdapter,new PatternTopic("topic_name2"));
        return redisMessageListenerContainer;
    }

}
