package com.hailer.news.common;

import rx.Subscriber;

/**
 * Created by moma on 17-8-28.
 */

public class LocalSubscriber<T> extends Subscriber<T> {

    private RxCallback<T> mRequestCallback;

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }

}
