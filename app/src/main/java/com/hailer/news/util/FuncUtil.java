package com.hailer.news.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 跟网络相关的工具类
 */
public class FuncUtil {
    
    private FuncUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 求yyyy-MM-dd HH:mm:ss格式的时间 字符串 与当前时间的间隔
     * @param str yyyy-MM-dd HH:mm:ss格式的时间
     */
    public static String time2Time(String str){

        String returnValue;

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date newsdate = simpleDateFormat.parse(str);
            Date nowDate = new Date();
            // 获得两个时间的毫秒时间差异
            long diff = nowDate.getTime() - newsdate.getTime();
            // 计算差多少天
            long day = diff / nd;
            // 计算差多少小时
            long hour = diff % nd / nh;
            // 计算差多少分钟
            long min = diff % nd % nh / nm;

            if(day != 0){
                returnValue = (day+"天前");
            }else if(hour != 0){
                returnValue = (hour+"小時前");
            }else{
                returnValue = (min+"分鐘前");
            }

        }catch(ParseException e){
            returnValue = str;
        }

        return returnValue;
    }

}
