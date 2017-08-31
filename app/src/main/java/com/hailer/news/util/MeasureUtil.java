package com.hailer.news.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hailer.news.R;
import com.socks.library.KLog;


/**
 * Fuction：测量工具类<p>
 */
public class MeasureUtil {


    public static int getNavigationBarHeight(Activity activity) {

        Resources resources = activity.getResources();

        int rid = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid > 0) {
            KLog.e("true or false" + resources
                    .getBoolean(rid) + "");
        }

        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            KLog.e("" + resources.getDimensionPixelSize(resourceId) + ""); //获取高度
            return resources.getDimensionPixelSize(resourceId);
        }

        return 0;
    }



    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param context （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param context （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 判断点击事件是否在View上
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null ) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            KLog.e("-----View v[left:"+left+";top:"+top+";bottom:"+bottom+";right:"+right+"]");
            KLog.e("-----event.getX():"+event.getX()+";event.getY():"+event.getY());
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        KLog.e("----v=null----");
        return false;
    }

    /**
     * 获取屏幕尺寸
     *
     * @param context 上下文
     * @return 屏幕尺寸像素值，下标为0的值为宽，下标为1的值为高
     */
    public static Point getScreenSize(Context context) {

        // 获取屏幕宽高
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point screenSize = new Point();
        wm.getDefaultDisplay().getSize(screenSize);
        return screenSize;
    }

}
