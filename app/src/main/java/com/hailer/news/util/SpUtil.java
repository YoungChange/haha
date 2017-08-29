package com.hailer.news.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.hailer.news.NewsApplication;

/**
 * Created by moma on 17-8-29.
 */

public class SpUtil {

    public static SharedPreferences getSharedPreferences() {
        return NewsApplication.getContext()
                .getSharedPreferences("hailer.news", Context.MODE_PRIVATE);
    }

    public static boolean readBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    public static void writeBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

}
