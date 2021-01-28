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

    public boolean tryLock(String targetId,String timeStamp){
        if(redisTemplate.opsForValue().setIfAbsent(targetId,timeStamp)){
            // 对应setnx命令，可以成功设置,也就是key不存在
            return true;
        }

        // 判断锁超时 - 防止原来的操作异常，没有运行解锁操作  防止死锁
        String currentLock = redisTemplate.opsForValue().get(targetId);
        // 如果锁过期 currentLock不为空且小于当前时间
        if(org.apache.commons.lang.StringUtils.isNotBlank(currentLock) && Long.parseLong(currentLock) < System.currentTimeMillis()){
            // 获取上一个锁的时间value 对应getset，如果lock存在
            String preLock =redisTemplate.opsForValue().getAndSet(targetId,timeStamp);

            // 假设两个线程同时进来这里，因为key被占用了，而且锁过期了。获取的值currentLock=A(get取的旧的值肯定是一样的),两个线程的timeStamp都是B,key都是K.锁时间已经过期了。
            // 而这里面的getAndSet一次只会一个执行，也就是一个执行之后，上一个的timeStamp已经变成了B。只有一个线程获取的上一个值会是A，另一个线程拿到的值是B。
            if(org.apache.commons.lang.StringUtils.isNotBlank(preLock) && preLock.equals(currentLock) ){
                // preLock不为空且preLock等于currentLock，也就是校验是不是上个对应的商品时间戳，也是防止并发
                return true;
            }
        }
        return false;
    }

    public void tryUnlock(String target,String timeStamp){
        try {
            String currentValue = redisTemplate.opsForValue().get(target);
            if(org.apache.commons.lang.StringUtils.isNotBlank(currentValue) && currentValue.equals(timeStamp) ){
                // 删除锁状态
                redisTemplate.opsForValue().getOperations().delete(target);
            }
        } catch (Exception e) {
            log.error("警报！警报！警报！解锁异常{}",e);
        }
    }
}
