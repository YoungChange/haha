package com.hailer.news.common;

import android.support.annotation.NonNull;

import com.socks.library.KLog;

import rx.Subscriber;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moma on 17-8-28.
 */

public class LocalSubscriber<T> extends Subscriber<T> {

    private RxCallback<T> mRequestCallback;

    public LocalSubscriber(@NonNull RxCallback<T> requestCallback) {
        mRequestCallback = checkNotNull(requestCallback, "RxCallback cannot be null");
    }

    @Override
    public void onCompleted() {
        KLog.i("LocalSubscriber->onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        mRequestCallback.requestError(ErrMsg.UNKNOW_ERROR);
    }

    @Override
    public void onNext(T t) {
        mRequestCallback.requestSuccess(t);
    }

}
