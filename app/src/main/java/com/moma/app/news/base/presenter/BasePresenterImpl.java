package com.moma.app.news.base.presenter;

import android.support.annotation.CallSuper;


import com.moma.app.news.base.view.BaseView;
import com.moma.app.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * Fuction: 代理的基类实现<p>
 */
public class BasePresenterImpl<T extends BaseView, V> implements BasePresenter, RequestCallback<V> {

    protected Subscription mSubscription;
    protected T mView;

    public BasePresenterImpl(T view) {
        mView = view;
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onDestroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mView = null;
    }


    @Override
    public void beforeRequest() {
        mView.showProgress();
    }

    @CallSuper
    @Override
    public void requestError(String msg) {
        mView.hideProgress();
    }

    @Override
    public void requestComplete() {
        mView.hideProgress();
    }

    @Override
    public void requestSuccess(V data) {

    }
}
