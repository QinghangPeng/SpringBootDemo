package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTest {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTest.class);

//    @PostConstruct
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void pushDataToRedis() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        logger.info("Time:" + time + "调用定时任务");
    }
}
