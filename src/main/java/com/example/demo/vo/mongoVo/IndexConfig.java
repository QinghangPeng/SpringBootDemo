package com.example.demo.vo.mongoVo;

import java.util.Map;

/**
 * @ClassName IndexConfig
 * @Description
 * @Author jackson
 * @Date 2019/8/27 15:30
 * @Version 1.0
 **/
public class IndexConfig {
    private String name;
    private Map<String,Integer> fields;

    public IndexConfig(Map<String, Integer> fields) {
        this.fields = fields;
    }

    public IndexConfig(String name, Map<String, Integer> fields) {
        this.name = name;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getFields() {
        return fields;
    }

    public void setFields(Map<String, Integer> fields) {
        this.fields = fields;
    }
}
