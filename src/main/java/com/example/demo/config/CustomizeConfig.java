package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName CustomizeConfig
 * @Description
 * @Author jackson
 * @Date 2019/8/28 10:14
 * @Version 1.0
 **/
@ConfigurationProperties(prefix = "customize")
public class CustomizeConfig {

    private String ifShard;

    public String getIfShard() {
        return ifShard;
    }

    public void setIfShard(String ifShard) {
        this.ifShard = ifShard;
    }
}
