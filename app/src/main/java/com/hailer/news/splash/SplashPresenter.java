package com.hailer.news.splash;

import android.content.Context;
import android.content.Intent;

import com.hailer.news.NewsApplication;
import com.hailer.news.news.NewsActivity;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by moma on 17-8-16.
 * 闪屏页
 */

public class SplashPresenter implements SplashContract.Presenter {
    private SplashContract.View mView;
    private SplashContract.Model mModle;

    public SplashPresenter(SplashContract.View view, SplashContract.Model modle) {
        mView = view;
        mModle = modle;
    }

    @Override
    public void getAdvertising() {
        Subscription subscribe = mModle.getAdvertising()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        mView.showAdvertising();
                    }
                });
    }

    @Override
    public void startNewsActivity() {
        Context context = NewsApplication.getContext();
        context.startActivity(new Intent(context, NewsActivity.class).setFlags(Intent. FLAG_ACTIVITY_NEW_TASK ));
    }
}
