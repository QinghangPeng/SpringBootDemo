package com.example.demo.config;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @ClassName PoolProvider
 * @Description
 * @Author jackson
 * @Date 2019/8/7 17:39
 * @Version 1.0
 **/
@Component
public class PoolProvider {

    private Map<String, ExecutorService> executors = new HashMap<>();

    Logger logger = LoggerFactory.getLogger(PoolProvider.class);

    @Autowired
    private DynamicProperties properties;

    public RejectedExecutionHandler rejectedExecutionHandler = (r, executor) -> {
        if (!executor.isShutdown()) {
            try {
                executor.getQueue().put(r);
            } catch (Exception e) {
                logger.error("retry put task error", e);
            }
        }
    };

    public ExecutorService getPool(String type) {
        ExecutorService executor = MapUtils.getObject(executors, type);
        if (executor != null) {
            return executor;
        }
        int taskSize = properties.getBatchPoolTaskSize();
        int queueSize = properties.getBatchPoolQueueSize();
        executor = new ThreadPoolExecutor(taskSize, taskSize, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueSize), new CustomThreadConfig(type + "_POOL"), rejectedExecutionHandler);
        executors.put(type, executor);
        return executor;
    }
}
