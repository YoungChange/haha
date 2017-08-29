package com.hailer.news.model;

import android.support.annotation.NonNull;

import com.hailer.news.NewsApplication;
import com.hailer.news.R;
import com.hailer.news.api.APIConfig;
import com.hailer.news.api.RetrofitService;
import com.hailer.news.common.ErrMsg;
import com.hailer.news.common.LocalSubscriber;
import com.hailer.news.common.RemoteSubscriber;
import com.hailer.news.common.RxCallback;
import com.hailer.news.util.SpUtil;
import com.hailer.news.util.bean.ChannelInfo;
import com.hailer.news.util.bean.NewsChannelBean;
import com.hailer.news.util.daogen.ChannelInfoDao;
import com.hailer.news.util.daogen.DaoSession;
import com.socks.library.KLog;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Fuction:
 */
public class LocalDataSource {

    private ChannelInfoDao channelsDao;
    private Query<ChannelInfo> channelsQuery;

    private RxCallback mCallBack;

    public LocalDataSource(@NonNull RxCallback callback){
        mCallBack = checkNotNull(callback, "callback cannot be null");
    }

    public LocalDataSource(){

    }

    public void getChannel(){



            Observable.create(new Observable.OnSubscribe<List<NewsChannelBean>>() {
                    @Override
                    public void call(Subscriber<? super List<NewsChannelBean>> subscriber) {

                        NewsChannelBean bean1 = new NewsChannelBean("即時", "0", "category");
                        NewsChannelBean bean4 = new NewsChannelBean("社會", "6", "category");
                        NewsChannelBean bean2 = new NewsChannelBean("體育", "2", "category");
                        NewsChannelBean bean3 = new NewsChannelBean("科技", "1", "category");


                        List<NewsChannelBean> newsChannels = new ArrayList<NewsChannelBean>();
                        newsChannels.add(bean1);

                        newsChannels.add(bean2);
                        newsChannels.add(bean3);
                        newsChannels.add(bean4);


                        subscriber.onNext(newsChannels);
        //                subscriber.onCompleted();


                    }
            })
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<NewsChannelBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
//                        mCallBack.requestError(e.getLocalizedMessage() + "\n" + e);\
                        mCallBack.requestError(ErrMsg.UNKNOW_ERROR);
                    }

                    @Override
                    public void onNext(List<NewsChannelBean> newsChannels) {
                        mCallBack.requestSuccess(newsChannels);
                    }
            });
    }

    public void getUserChannel(RxCallback callback){

        DaoSession daoSession = ((NewsApplication)NewsApplication.getContext()).getDaoSession();
        channelsDao = daoSession.getChannelInfoDao();

        KLog.e("初始化了数据库了吗？ " + SpUtil.readBoolean("initDb"));
        if (!SpUtil.readBoolean("initDb")) {

            List<String> channelName = Arrays.asList(NewsApplication.getContext().getResources()
                    .getStringArray(R.array.news_channel));

            List<String> channelSlug = Arrays.asList(NewsApplication.getContext().getResources()
                    .getStringArray(R.array.news_channel_slug));

            for (int i = 0; i < channelName.size(); i++) {
                ChannelInfo channel = new ChannelInfo((long)i,i,channelName.get(i),channelSlug.get(i),"",i<4);
                channelsDao.insert(channel);
            }
            SpUtil.writeBoolean("initDb", true);
            KLog.e("数据库初始化完毕！");
        }


        Observable.create(new Observable.OnSubscribe<List<ChannelInfo>>() {
            @Override
            public void call(Subscriber<? super List<ChannelInfo>> subscriber) {
                channelsQuery = channelsDao.queryBuilder()
                        .where(ChannelInfoDao.Properties.Sign.eq(true))
                        .orderAsc(ChannelInfoDao.Properties.Id)
                        .build();
                List<ChannelInfo> channels = channelsQuery.list();
                subscriber.onNext(channels);
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe(new LocalSubscriber<List<ChannelInfo>>(callback));
    }

    public void getOtherChannel(RxCallback callback){
        Observable.create(new Observable.OnSubscribe<List<ChannelInfo>>() {
            @Override
            public void call(Subscriber<? super List<ChannelInfo>> subscriber) {
                DaoSession daoSession = ((NewsApplication)NewsApplication.getContext()).getDaoSession();
                channelsDao = daoSession.getChannelInfoDao();
                channelsQuery = channelsDao.queryBuilder().where(ChannelInfoDao.Properties.Sign.eq(false)).orderAsc(ChannelInfoDao.Properties.Id).build();
                List<ChannelInfo> channels = channelsQuery.list();
                subscriber.onNext(channels);
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe(new LocalSubscriber<List<ChannelInfo>>(callback));
    }

    public void updateChannel(RxCallback callback,List<ChannelInfo> allChannels){
        DaoSession daoSession = ((NewsApplication)NewsApplication.getContext()).getDaoSession();
        channelsDao = daoSession.getChannelInfoDao();
        channelsQuery = channelsDao.queryBuilder().orderAsc(ChannelInfoDao.Properties.Id).build();

        for (int i = 0;i<allChannels.size();i++) {
            ChannelInfo channelInfo = allChannels.get(i);
            channelInfo.setPosition(i);
            channelsDao.update(channelInfo);
        }
    }

    public void channelDbAddItem(RxCallback callback, String channelName){
        KLog.e("做增操作:channelName " + channelName + ";");
        DaoSession daoSession = ((NewsApplication)NewsApplication.getContext()).getDaoSession();
        channelsDao = daoSession.getChannelInfoDao();
        channelsQuery = channelsDao.queryBuilder().where(ChannelInfoDao.Properties.Sign.eq(true)).orderAsc(ChannelInfoDao.Properties.Id).build();
        channelsQuery.list();
        // 找到它的信息
//        final NewsChannelTable table = dao.queryBuilder()
//                .where(NewsChannelTableDao.Properties.NewsChannelName
//                        .eq(channelName)).unique();
//
//        // 它原来的位置
//        final int originPos = table.getNewsChannelIndex();
//
//        // 得到现在应该所处位置
//        final long toPos = dao.queryBuilder()
//                .where(NewsChannelTableDao.Properties.NewsChannelSelect
//                        .eq(true)).buildCount().count();
//
//        // gt大于   lt小于   ge大于等于   le 小于等于
//
//        // 找到比它位置小的没被选中的
//        final List<NewsChannelTable> smallChannelTables = dao.queryBuilder()
//                .where(NewsChannelTableDao.Properties.NewsChannelIndex
//                                .lt(originPos),
//                        NewsChannelTableDao.Properties.NewsChannelSelect
//                                .eq(false)).build().list();
//        for (NewsChannelTable s : smallChannelTables) {
//            s.setNewsChannelIndex(s.getNewsChannelIndex() + 1);
//            dao.update(s);
//        }
//
//        // 更新它
//        table.setNewsChannelSelect(true);
//        table.setNewsChannelIndex((int) toPos);
//
//        dao.update(table);
//
    }

    public void channelDbRemoveItem(RxCallback callback, String channelName){
        DaoSession daoSession = ((NewsApplication)NewsApplication.getContext()).getDaoSession();
        channelsDao = daoSession.getChannelInfoDao();
        channelsQuery = channelsDao.queryBuilder().where(ChannelInfoDao.Properties.CategoryName.eq(channelName)).orderAsc(ChannelInfoDao.Properties.Id).build();
        ChannelInfo channelInfo = channelsQuery.list().get(0);
        channelInfo.setSign(false);
        channelsDao.update(channelInfo);
    }

    public void channelDbSwap(RxCallback callback, final int fromPos, final int toPos){

    }

}
