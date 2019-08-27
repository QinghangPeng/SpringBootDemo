package com.example.demo.vo.mongoVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @ClassName IndexReq
 * @Description
 * @Author jackson
 * @Date 2019/7/30 11:32
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@ApiModel("创建带索引collection入参")
public class IndexReq {

    private String colName;
    private List<IndexConfig> list;

    public static class IndexConfig {
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

}
