package com.example.demo.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
    //获取指定月的第一天和最后一天
    public static Map<String,String> getMothDate(String moth){
        LocalDateTime dateTime = LocalDateTime.parse(moth + "-01T00:00");
        //获取月的第一天
        LocalDateTime firstDay = dateTime.with(TemporalAdjusters.firstDayOfMonth());
        //获取月的最后一天
        LocalDateTime  lastDay = dateTime.with(TemporalAdjusters.firstDayOfMonth());
        //获取月的第一天0时0分0秒
        LocalDateTime firstDayWith = dateTime.with(TemporalAdjusters.firstDayOfMonth())
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        ////获取月的最后一天的23点59分59秒
        LocalDateTime lastDayWith = dateTime.with(TemporalAdjusters.lastDayOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59);
        Map<String,String> map = new HashMap<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        map.put("startTime",dtf.format(firstDayWith));
        map.put("endTime",dtf.format(lastDayWith));
        return map;
    }

    //两个字符串类型的时间相减
    public static Double getTimeCha(String startTime,String endTime) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //把字符类型的时间转化为标准时间样式
        Date firstDateTimeDate = format.parse(startTime);
        Date secondDateTimeDate = format.parse(endTime);
        //得到时间的总毫秒数
        long firstDateMilliSeconds = firstDateTimeDate.getTime();
        long secondDateMilliSeconds = secondDateTimeDate.getTime();
        long subDateMilliSeconds = secondDateMilliSeconds - firstDateMilliSeconds;
        //得到相差的小时
        double  hours = (float)subDateMilliSeconds / (1000 * 60 * 60);
        Double floorHours =Double.valueOf(new DecimalFormat( ".0" ).format(hours));
        return floorHours;
    }

    //获取上月的年-月
    public static String getLastMoth(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上1个月
        date = calendar.getTime();
        String lastMonth = sdf.format(date);
        return lastMonth;
    }
}
