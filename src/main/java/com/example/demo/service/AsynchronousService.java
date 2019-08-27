package com.example.demo.service;

import com.example.demo.config.PoolProvider;
import com.example.demo.vo.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @ClassName AsynchronousService
 * @Description
 * @Author jackson
 * @Date 2019/5/20 16:14
 * @Version 1.0
 **/
@Service
@Slf4j
public class AsynchronousService {

    @Autowired
    @Qualifier("demoTask")
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private PoolProvider poolProvider;

    /**
     * 获取一个指定名字的线程池
     */
    private ExecutorService pool;

    @PostConstruct
    public void init() {
        this.pool = poolProvider.getPool("batch");
    }

    @Async
    public String testAsynch() {
        try{
            for (int i=0;i<=100;i++) {
                log.info(Thread.currentThread().getName() + "----------------异步：>" + i);
                Thread.sleep(2000);
            }

            return "执行异步任务完毕";
        } catch(Exception e) {
            e.printStackTrace();
        }
        return Thread.currentThread().getName() + "执行完毕";
    }

    public String getName() {
        log.info("=======================进入方法");
        printSomething();
        diffFromCallAndRun();
        log.info("=======================离开方法");
        return "name";
    }

    public void printSomething() {
        taskExecutor.submit(() -> {
            try{
                for (int i=0;i<=100;i++) {
                    log.info(Thread.currentThread().getName() + "----------------异步：>" + i);
                    Thread.sleep(1000);
                }
            } catch(Exception e) {
                log.error("there is something wrong:{}",e);
            }
        });
    }

    public void diffFromCallAndRun() {
        /**
         *  Runnable 接口，无返回值,不使用Future接收
         */
        taskExecutor.submit(() -> {
            try {
                for (int i = 0; i <= 10; i++) {
                    log.info(Thread.currentThread().getName() + "----------------异步2：>" + i);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                log.error("there is something wrong:{}", e);
            }
        });

        //当然  线程多的时候可以用list，然后循环取值，单线程可以省略
        List<Future> futures = new ArrayList<>();

        Future<?> infoFuture = taskExecutor.submit(() ->
                log.info("====================this is a test for runnable with future class===============")
        );
        futures.add(infoFuture);
        for (Future future : futures) {
            try{
                future.get();
            } catch(Exception e) {
                log.error("test for runnable with future class error:{}",e);
            }
        }

        /**
         *  Callable接口，有返回值
         *  可以和Future联合使用
         */
        try{
            Future<Vehicle> vehicleFuture = taskExecutor.submit(() -> {
                Vehicle vehicle = new Vehicle();
                vehicle.setVin("vin");
                vehicle.setTboxId("tboxId");
                vehicle.setName("name");
                return vehicle;
            });
            Vehicle v = vehicleFuture.get();
            log.info("vehicle info:{}",v.toString());
        } catch(Exception e) {
            log.error("callable error:{}",e);
        }
    }

    public void getThreadPool() {
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> log.info("利用线程池创建自定义名字的线程:{}",Thread.currentThread().getName()));
        }
    }
}
