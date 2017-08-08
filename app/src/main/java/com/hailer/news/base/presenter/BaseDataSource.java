package com.hailer.news.base.presenter;

import android.support.annotation.CallSuper;

import com.hailer.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * Fuction: 代理的基类<p>
 */
public interface BaseDataSource {

    interface LoginCallback {
        void loginSuccess();

        void loginFailed();
    }

    interface GetDataCallback{
        void getDataSuccess();

        void getDataFailed();
    }

}
