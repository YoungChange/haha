package com.hailer.news.common;

import android.support.annotation.CallSuper;

import com.socks.library.KLog;


import retrofit2.adapter.rxjava.HttpException;
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
        KLog.e("onError, e="+e);
        int err = ErrMsg.LOAD_DATA_ERROR;
        if (e instanceof HttpException) {
            int errCode = ((HttpException) e).code();
            KLog.e("onError, http error code =" + errCode);

            //状态码是2xx是成功
            if (errCode == 201 || errCode == 200) {
                err = ErrMsg.SUCCESS;
            }
            if (errCode == 504) {
                mRequestCallback.requestError(err);
            }
        }

    }

    @CallSuper
    @Override
    public void onNext(T t) {
        mRequestCallback.requestSuccess(t);
    }
}
