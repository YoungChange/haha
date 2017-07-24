package com.moma.app.news.base;

import android.support.annotation.CallSuper;

import com.fasterxml.jackson.databind.JsonMappingException;

import com.moma.app.news.util.callback.RequestCallback;
import com.socks.library.KLog;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/8/24 0024.
 * 把回调各个方法统一处理，并且这里对返回错误做了统一处理
 */
public class BaseSubscriber<T> extends Subscriber<T> {

    private RequestCallback<T> mRequestCallback;

    public BaseSubscriber(RequestCallback<T> requestCallback) {
        mRequestCallback = requestCallback;
    }

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        if (mRequestCallback != null) {
            mRequestCallback.beforeRequest();
        }
    }

    @CallSuper
    @Override
    public void onCompleted() {
        if (mRequestCallback != null) {
            mRequestCallback.requestComplete();
        }
    }

    @CallSuper
    @Override
    public void onError(Throwable e) {
        if (mRequestCallback != null) {
            mRequestCallback.requestError("请求错误：");
        }
    }

    @CallSuper
    @Override
    public void onNext(T t) {
        if (mRequestCallback != null) {
            mRequestCallback.requestSuccess(t);
        }
    }
}