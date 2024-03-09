package com.yc.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: labour_teach
 * @description: 大几计算
 * @author: 作者 huchaojie
 * @create: 2022-11-05 15:05
 */
public class SchoolYearUtils {

    public static void main(String[] args) throws ParseException {
    }

    /**
     * 判断学生是否毕业
     * @param year
     * @return
     */
    public static String schoolYear(int year) {
        String res = null;
        int time_year;
        int time_month;
        Date time = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
        res = simpleDateFormat.format(time);
        time_year=Integer.parseInt(res.substring(0,4));
        time_month=Integer.parseInt(res.substring(5,7));

        //处理时间超时
        if(year>time_year){
            res= "-1";
        }
        //相同年，但是月份还不到9月
        if(year==time_year){
            if(time_month<9){
                res="-1";
            } else if(time_month>=9){
                res="大一";
            }
        }
        if(time_year>year){
            //时差
            int temp=time_year-year;
            if(temp<=3){
                String []Class={"大一","大二","大三","大四"};
                res=Class[temp-1];
                /*if(time_month<9){
                    res+="下学期";
                }else {
                    if(temp<3){//不是大四
                        res=Class[temp]+"上学期";
                    }else if(temp==3){//大四
                        res="上半年是大四下学期啦，现在已经你毕业几个月了";
                    }
                }*/
            } else if(temp>3){
                //毕业多少年
                int year1=temp-3;
                //毕业多少月
                int momth1=time_month;
                if(time_month<6){
                    year1-=1;
                    momth1=time_month+12-6;
                }
                res=String.valueOf( ((year1-1) * 12) + (momth1-6) );
            }
        }
        return res;
    }
}
