package com.hailer.news.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.hailer.news.common.Const;

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
}
