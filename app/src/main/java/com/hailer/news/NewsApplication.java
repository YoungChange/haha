package com.hailer.news;


import android.app.Application;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;
import android.content.Context;
import com.hailer.news.common.Const;
import com.hailer.news.util.daogen.DaoMaster;
import com.hailer.news.util.daogen.DaoSession;
import com.socks.library.KLog;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import org.greenrobot.greendao.database.Database;
import java.util.UUID;
import static android.text.TextUtils.isEmpty;


public class NewsApplication extends MultiDexApplication{
    private static Context sApplicationContext;
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    //greenDao相关
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        // 如果检测到某个 activity 有内存泄露，LeakCanary 就是自动地显示一个通知
        //mRefWatcher = LeakCanary.install(this);

        initDatabase();

        sApplicationContext = this;
        KLog.init(BuildConfig.DEBUG);

        sAnalytics = GoogleAnalytics.getInstance(this);

    }

    // 获取ApplicationContext
    public static Context getContext() {
        return sApplicationContext;
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }


    /**
     * greenDao 初始化数据库
     */
    private void initDatabase(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"channels-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    /**
     * 获取DaoSession对象
     * @return
     */
    public DaoSession getDaoSession() {
        return daoSession;
    }



    public static String getUUID(){
        SharedPreferences preferences = getContext().getSharedPreferences(
                Const.Identification.PREFERENCE, Context.MODE_PRIVATE);
        String uuid = null;
        if(preferences != null){
            uuid = preferences.getString(Const.Identification.UUID, "");
        }
        if(isEmpty(uuid)){
            uuid = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Const.Identification.UUID, uuid);
            editor.apply();
        }
        return uuid;
    }
}
