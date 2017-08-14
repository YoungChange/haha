package com.hailer.news.news;

import android.support.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hailer.news.UserManager;
import com.hailer.news.base.ErrMsg;
import com.hailer.news.util.bean.NewsChannelBean;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Fuction:
 */
public class LocalDataSource {
    private RxCallback mCallBack;

    public LocalDataSource(@NonNull RxCallback callback){
        mCallBack = checkNotNull(callback, "callback cannot be null");
    }

    void getChannel(){

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

}
