package com.example.demo.util;

import com.alibaba.excel.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     *  加锁
     * @param key
     * @param value 当前时间+TIMEOUT时间
     * @return
     */
    public boolean lock(String key, String value) {
        //1.第一步，如果主键不存在，那么直接加锁，返回成功
        if (redisTemplate.opsForValue().setIfAbsent(key,value)) {
            return true;
        }
        //2.如果第一步加锁失败，那么拿该主键的过期时间
        String currentValue = redisTemplate.opsForValue().get(key);
        //2.1 如果锁过期
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //2.2 获取上一个锁的时间：这里比如来了两个线程：A,B，并且他们的过期时间都为C
            /**
             * 这里只可能一个线程先进到getAndSet方法，
             * 比如A先拿到，那么oldvalue==currentValue，并且A将自己的过期时间C设置到了该主键中
             * 那么当B线程调用该方法时，oldvalue是==C的，所以B线程拿不到该锁
             * 这样就保证了只有A这一个线程拿到锁
             */
            String oldValue = redisTemplate.opsForValue().getAndSet(key,value);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key,String value) {
        try{
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch(Exception e) {
            log.error("redis分布式锁 解锁异常，{}",e);
        }
    }
}
