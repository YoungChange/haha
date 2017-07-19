package com.moma.app.news.home.model;

import com.moma.app.news.util.bean.NewsChannelBean;
import com.moma.app.news.util.callback.RequestCallback;
import com.socks.library.KLog;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * ClassName: INewsInteractorImpl<p>
 * Author: oubowu<p>
 * Fuction: 新闻Model层接口实现,数据库操作，第一次初始化频道，之后查询选中的频道<p>
 * CreateDate: 2016/2/20 15:05<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public class INewsInteractorImpl implements INewsInteractor<List<NewsChannelBean>> {

    @Override
    public Subscription operateChannel(final RequestCallback<List<NewsChannelBean>> callback) {

        return Observable.create(new Observable.OnSubscribe<List<NewsChannelBean>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelBean>> subscriber) {

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callback.beforeRequest();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<NewsChannelBean>>() {
                    @Override
                    public void onCompleted() {
                        callback.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.getLocalizedMessage() + "\n" + e);
                        callback.requestError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(List<NewsChannelBean> newsChannels) {
                        callback.requestSuccess(newsChannels);
                    }
                });
    }
}
