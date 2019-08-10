package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName DynamicProperties
 * @Description
 * @Author jackson
 * @Date 2019/8/7 17:50
 * @Version 1.0
 **/
@ConfigurationProperties(prefix = "pool")
public class DynamicProperties {

    private int batchPoolTaskSize;
    private int batchPoolQueueSize;

    public int getBatchPoolTaskSize() {
        return batchPoolTaskSize;
    }

    public void setBatchPoolTaskSize(int batchPoolTaskSize) {
        this.batchPoolTaskSize = batchPoolTaskSize;
    }

    public int getBatchPoolQueueSize() {
        return batchPoolQueueSize;
    }

    public void setBatchPoolQueueSize(int batchPoolQueueSize) {
        this.batchPoolQueueSize = batchPoolQueueSize;
    }
}
