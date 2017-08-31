package com.hailer.news.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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
        long ns = 1000;

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
            //计算差多少秒
            long sec = diff % nd % nh % nm / ns;

            if(day != 0){
                returnValue = (day+"天前");
            }else if(hour != 0){
                returnValue = (hour+"小時前");
            }else if(min != 0){
                returnValue = (min+"分鐘前");
            }else{

                returnValue = ((sec>0?sec:0)+"秒前");
            }

        }catch(ParseException e){
            returnValue = str;
        }

        return returnValue;
    }


    public static boolean isAvilible(Context context, String packageName )
    {
        final PackageManager packageManager = context.getPackageManager();

        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for ( int i = 0; i < pinfo.size(); i++ )
        {
            if(pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    public static void dynamicSetTabLayoutMode(TabLayout tabLayout) {
        int tabTotalWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0);
            tabTotalWidth += view.getMeasuredWidth();
        }
        if (tabTotalWidth <= MeasureUtil.getScreenSize(tabLayout.getContext()).x) {
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

}
