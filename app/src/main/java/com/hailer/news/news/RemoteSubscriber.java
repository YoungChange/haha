package com.hailer.news.news;

import android.support.annotation.CallSuper;

import com.hailer.news.util.callback.RequestCallback;
import com.socks.library.KLog;


import rx.Subscriber;

import static com.google.common.base.Preconditions.checkNotNull;
import android.support.annotation.NonNull;

/**
 * 把回调各个方法统一处理，并且这里对返回错误做了统一处理
 */
public class RemoteSubscriber<T> extends Subscriber<T> {

    private RxCallback<T> mRequestCallback;

    public RemoteSubscriber(@NonNull RxCallback<T> requestCallback) {
        mRequestCallback = checkNotNull(requestCallback, "RxCallback cannot be null");
    }

    @CallSuper
    @Override
    public void onCompleted() {

    }

    @CallSuper
    @Override
    public void onError(Throwable e) {
        KLog.e("onError, bailei ..... e="+e);
        mRequestCallback.requestError("请求错误：");
    }

    @CallSuper
    @Override
    public void onNext(T t) {
        mRequestCallback.requestSuccess(t);
    }
}
