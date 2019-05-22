package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
}
