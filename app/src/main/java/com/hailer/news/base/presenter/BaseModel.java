package com.hailer.news.base.presenter;

import android.support.annotation.CallSuper;

import com.hailer.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * Fuction: 代理的基类
 */
public class BaseModel<T> implements RequestCallback<T> {

    protected Subscription mSubscription;

    public void onDestroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void beforeRequest() {

    }

    @CallSuper
    @Override
    public void requestError(String msg) {

    }

    @Override
    public void requestComplete() {

    }

    @Override
    public void requestSuccess(T data) {

    }

}
