package com.example.demo.vo.es;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Document
 * @Description
 * @Author jackson
 * @Date 2019/1/25 16:05
 * @Version 1.0
 **/
public class Document {

    private String indexName;
    private String type;
    private String id;
    private Map<String, Object> data = new HashMap<>();

    public Document(String indexName, String type, String id, Map<String, Object> data) {
        this.indexName = indexName;
        this.type = type;
        this.id = id;
        this.data = data;
    }

    public Document(String indexName, String type, String id) {
        this.indexName = indexName;
        this.type = type;
        this.id = id;
    }

    public Document(String indexName, String type) {
        this.indexName = indexName;
        this.type = type;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Document{" +
                "indexName='" + indexName + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", data=" + data +
                '}';
    }
}
