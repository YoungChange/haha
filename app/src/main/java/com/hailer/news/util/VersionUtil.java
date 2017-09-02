package com.hailer.news.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.hailer.news.NewsApplication;
import com.hailer.news.R;
import com.hailer.news.common.Const;
import com.socks.library.KLog;

/**
 * Created by moma on 17-8-31.
 */

public class VersionUtil {
    public static void showMarket(Context context) {
        final String appPackageName = Const.App.PACKAGE_NAME;
        try {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.android.vending");
            ComponentName comp = new ComponentName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity");
            launchIntent.setComponent(comp);
            launchIntent.setData(Uri.parse("market://details?id="+appPackageName));

            context.startActivity(launchIntent);

        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }



    /**
     * get App versionCode
     * @param context
     * @return
     */
    public static String getVersionCode(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=String.valueOf(packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * get App versionName
     * @param context
     * @return 1.1.1
     */
    public static String getVersionName(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionName="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    public static boolean checkVersion(String[] remoteVersionArray,String[] localVersionArray){
        try{
            KLog.e("remoteVersionArray.length:"+remoteVersionArray.length);
            KLog.e("localVersionArray.length:"+localVersionArray.length);
            for(int i=0;i<remoteVersionArray.length;i++){
                if(Integer.valueOf(remoteVersionArray[i])>=Integer.valueOf(localVersionArray[i])){
                    return true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            KLog.e("VersionInfo Error");
        }

        return false;
    }
}
