package com.example.demo.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName KafkaConsumersConfig
 * @Description
 * @Author jackson
 * @Date 2019/4/9 18:39
 * @Version 1.0
 **/
@Configuration
public class KafkaConsumersConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafkaConsumers.testGroupId}")
    private String testGroupId;


    /**
     *  特定消费者
     * @return
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,String>> testListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        /*factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL);*/
        factory.setConsumerFactory(vehStatusConsumerFactory(testGroupId));
        /**
         *  实现多分区，多线程消费
         */
        factory.setConcurrency(3);
        return factory;
    }

    public Map<String, Object> getCommonProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        /*properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000);*/
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return properties;
    }

    public ConsumerFactory<String, String> vehStatusConsumerFactory(String groupId) {
        Map<String, Object> properties = getCommonProperties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        return new DefaultKafkaConsumerFactory<>(properties);
    }
}
