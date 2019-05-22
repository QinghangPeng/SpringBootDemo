package com.example.demo.kafka;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CustomerPatitioner
 * @Description
 * @Author jackson
 * @Date 2019/4/29 14:01
 * @Version 1.0
 **/
public class CustomerPatitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] bytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (value == null) {
            return 0;
        }
        String s = value.toString();
        JSONObject json = JSONObject.parseObject(s);
        String vin = json.getString("vin");
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        if(numPartitions <= 1 || vin == null) {
            return 0;
        }
        //进行逻辑分区
        return (Math.abs(Utils.murmur2(vin.getBytes())) % (numPartitions - 1));
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
