package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.Holiday;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
public class HolidayUtil {
    /**
     * 使用的百度日历的api，由于法定节假日是由国务院在年底发布后更新，所以该部分数据不能做一次性更新处理，只能存储近一两个月的假期信息
     */
    public static final String HOLIDAY_API = "https://sp1.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?tn=wisetpl&format=json&resource_id=39043&query=%s";

    /**
     * api data : {
     *     status: 0,
     *     t: "",
     *     set_cache_time: "",
     *     data: [{
     *              ***,
     *              almanac: [{
     *                      status: "1",    // 1 法定休假  2 调休  默认为null
     *                      cnDay: "一"，    // 六、日为休假 若status为2 则需要上班
     *                      day: "27",
     *                      month: "9",
     *                      year: "2021",
     *                      oDate: 2021-10-31T16:00:00.000Z,
     *                      ...
     *                  }, ...]
     *          }]
     * }
     *
     * @param year      年
     * @param month     月
     * @return
     */
    public static List<Holiday> getMonthHoliday(Integer year, Integer month) {
        Map<String, String> holiday = HttpSendUtil.sendRequest("GET", String.format(HOLIDAY_API, parseHolidayTimeQuery(year, month)), null, null, "");
        if (holiday != null && holiday.containsKey("resBody")) {
            JSONObject res = JSON.parseObject(holiday.get("resBody"));
            JSONArray calendarData = res.getJSONArray("data");
            if (calendarData != null && calendarData.size() > 0) {
                JSONObject calendarObj = (JSONObject) calendarData.get(0);
                JSONArray almanac = calendarObj.getJSONArray("almanac");
                List<Holiday> holidayList = new ArrayList<>(31);
                String monthS = month.toString();
                String yearS = year.toString();
                for (Object day: almanac) {
                    JSONObject jsonDay = (JSONObject) day;
                    // 接口会查询上月（部分）、当月、下月（部分）三个月左右数据
                    String monthDay = jsonDay.getString("day");
                    if (monthS.equals(jsonDay.getString("month")) && yearS.equals(jsonDay.getString("year"))) {
                        String monthTime = monthS ;
                        String dayTime = monthDay ;
                        if(Integer.parseInt(monthS) < 10){
                            monthTime = "0" + monthTime;
                        }
                        if(Integer.parseInt(monthDay) < 10){
                            dayTime = "0" + dayTime;
                        }
                        String date = yearS + "-" + monthTime + "-" + dayTime;
                        if ("1".equals(jsonDay.getString("status"))) {
                            // 法定节假日
                            holidayList.add(new Holiday(date, year, month, Integer.parseInt(monthDay), (byte) 1));
                        } else if (("六".equals(jsonDay.getString("cnDay")) || "日".equals(jsonDay.getString("cnDay"))) && !"2".equals(jsonDay.getString("status"))) {
                            // 周六日（过滤调休）
                            holidayList.add(new Holiday(date, year, month, Integer.parseInt(monthDay), (byte) 0));
                        }
                    }
                }
                return holidayList;
            }
        }
        return Collections.emptyList();
    }

    public static String parseHolidayTimeQuery(Integer year, Integer month) {
        String queryMonth = String.format("%d年%d月", year, month);
        try {
            return URLEncoder.encode(queryMonth, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return URLEncoder.encode(queryMonth);
        }
    }
}
