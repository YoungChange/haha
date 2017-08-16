package com.hailer.news.splash;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by moma on 17-8-16.
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
}
