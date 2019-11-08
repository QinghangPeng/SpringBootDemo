package com.example.demo.util;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName TimeUtil
 * @Description
 * @Author jackson
 * @Date 2019/7/8 15:31
 * @Version 1.0
 **/
public class TimeUtil {

    private static final String FORMAT = "yyyy-MM-dd";

    /**
     *  获取连续的时间区间
     * @param date 日期 asc: true --> date 为开始日期  asc：false --> date 为结束日期
     * @param interval 时间区间（多少天）
     * @param count 几个时间区间
     * @param asc 升序还是降序获取
     * @return
     */
    public static List<String[]> continuousDateInterval(String date, int interval, int count, boolean asc) {
        List<String[]> rst = new ArrayList<>(count);
        DateTime dateTime = new DateTime(date);
        for (int i = 0; i < count; i++) {
            String[] items = new String[2];
            if (asc) {
                items[0] = dateTime.toString(FORMAT);
                items[1] = dateTime.plusDays(interval).toString(FORMAT);
                rst.add(items);

                dateTime = dateTime.plusDays(interval + 1);
            } else {
                items[0] = dateTime.minusDays(interval).toString(FORMAT);
                items[1] = dateTime.toString(FORMAT);
                rst.add(items);

                dateTime = dateTime.minusDays(interval + 1);
            }
        }
        if (!asc) {
            Collections.reverse(rst);
        }
        return rst;
    }

    /**
     *  判断两个日期相差几天
     * @param from
     * @param to
     * @return
     */
    public static int daysBetweenTwoDate(String from,String to) {
        DateTime fromTime = new DateTime(from);
        DateTime toTime = new DateTime(to);
        Interval interval = new Interval(fromTime.getMillis(),toTime.getMillis());
        Period period = interval.toPeriod();
        return period.getDays();
    }

    public static void main(String[] args) {
        System.out.println(daysBetweenTwoDate("2019-07-01","2019-07-01"));
        List<String[]> strings = continuousDateInterval("2019-07-01", daysBetweenTwoDate("2019-07-01", "2019-07-01"), 6, false);
        strings.forEach(e -> System.out.println(e[0] + "-" + e[1]));
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
        System.out.println("cedf9cfdf219414c9a067337602f4540".length());
    }
}
