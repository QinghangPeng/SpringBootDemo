package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName RestConfig
 * @Description
 * @Author jackson
 * @Date 2019/3/11 15:31
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "es.elasticsearch")
public class RestConfig {

    private String clusterNodes;

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }
}
