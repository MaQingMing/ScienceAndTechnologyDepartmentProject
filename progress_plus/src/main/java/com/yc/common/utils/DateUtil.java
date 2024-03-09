package com.yc.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间处理工具类
 *
 * @author HuChaoJie
 */
@Slf4j
public class DateUtil {

    /**
     * 将时间转换为时间戳
     * yyyy-MM-dd HH:mm
     *
     * @param time
     * @return
     */
    public static long dateToStamp(String time) {
        long ts = 0;
        try {
            String stap;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = simpleDateFormat.parse(time);
            ts = date.getTime();//获取时间的时间戳
        } catch (ParseException e) {
            log.error(" dateToStamp Exception ",e);
        } finally {
            return ts;
        }
    }

    /*
     * 将时间戳转换为时间
     * yyyy-MM-dd HH:mm:ss
     */
    public static String stampToDate(String stap) {
        String time;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(stap);
        Date date = new Date(lt);
        time = simpleDateFormat.format(date);
        return time;
    }


    /*
     * 将 date 转换为 str 时间
     * yyyy-MM-dd
     */
    public static String strToDate(Date date) {
        String time;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = simpleDateFormat.format(date);
        return time;
    }

    /*
     * 将 date 转换为 str 时间
     * yyyy-MM-dd
     */
    public static String strToDates(Date date) {
        String time;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM_dd_HH_mm");
        time = simpleDateFormat.format(date);
        return time;
    }

    public static String strToDatess(Date date) {
        String time;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        time = simpleDateFormat.format(date);
        return time;
    }


    public static String parseDate(String strDate,String pattern){
        if(strDate==null || pattern==null || "".equals(strDate.trim()) ||"".equals(pattern.trim()) ){
            throw new IllegalArgumentException("字符串或日期格式为空");
        }
        Date date = null;
        try{
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            date=parser.parse(strDate);
            return strToDate(date,pattern);
        }catch (ParseException e) {
            log.error(" parseDate Exception ",e);
        }
        return strToDate(date,pattern);
    }


    /**
     * 将 date 转换为 str 时间
     * @param date
     * @param dateFormat yyyy-MM-dd
     * @return
     */
    public static String strToDate(Date date,String dateFormat) {
        if(null == dateFormat || "".equals(dateFormat)){
            dateFormat = "yyyy-MM-dd";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String time = simpleDateFormat.format(date);
        return time;
    }

    /**
     * String 时间 转 Date
     *
     * @param stap
     * @return
     */
    public static Date stringToDate(String stap) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = simpleDateFormat.parse(stap);
        } catch (Exception e) {
            return new Date();
        }
        return date;
    }

    /**
     * 获取过去7天内的日期
     *
     * @param days
     * @return
     */
    public static List<String> getPastDate(Integer days) {
        Calendar calendar = Calendar.getInstance();
        List<String> dateList = new ArrayList<String>();
        for (int i = 0; i < days; i++) {
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
            Date today = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            dateList.add(format.format(today));
        }
        return dateList;
    }

    /**
     *  当前日期加天数
     * @param days 天数
     * @return
     */
    public static String getDatePlusDays(int days){
        // 获取当前时间
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        // 把日期往后增加一天,整数  往后推,负数往前移动
        calendar.add(Calendar.DATE, days);
        // 这个时间就是日期往后推一天的结果
        date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    public static int getDatePoor(String end, String now) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date endDate = sdf.parse(end);
            Date nowDate = sdf.parse(now);

            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;
            // long ns = 1000;
            // 获得两个时间的毫秒时间差异
            long diff = endDate.getTime() - nowDate.getTime();
            // 计算差多少天
            long day = diff / nd;
            // 计算差多少小时
            long hour = diff % nd / nh;
            // 计算差多少分钟
            long min = diff % nd % nh / nm;
            // 计算差多少秒//输出结果
            // long sec = diff % nd % nh % nm / ns;
            return (int) day;
        } catch (ParseException e) {
            return 0;
        }
    }


    public static void main(String[] args) {
        System.out.println(getDatePlusDays(0).toString());
    }

}
