package com.hailer.news;

//import android.app.Application;
import android.support.multidex.MultiDexApplication;
import android.content.Context;

import com.hailer.news.BuildConfig;
import com.hailer.news.util.daogen.DaoMaster;
import com.hailer.news.util.daogen.DaoSession;
import com.socks.library.KLog;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.twitter.sdk.android.core.Twitter;

import org.greenrobot.greendao.database.Database;


public class NewsApplication extends MultiDexApplication {

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

        //setupDatabase();
        sApplicationContext = this;
        KLog.init(BuildConfig.DEBUG);

        sAnalytics = GoogleAnalytics.getInstance(this);

        initDatabase();
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



/*
    private void setupDatabase() {
        // // 官方推荐将获取 DaoMaster 对象的方法放到 Application 层，这样将避免多次创建生成 Session 对象
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constant.DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = BuildConfig.DEBUG;
        QueryBuilder.LOG_VALUES = BuildConfig.DEBUG;
    }
*/


}
