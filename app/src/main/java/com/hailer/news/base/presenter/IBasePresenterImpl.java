package com.hailer.news.base.presenter;

import android.support.annotation.CallSuper;


import com.hailer.news.base.view.BaseView;
import com.hailer.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * Fuction: 代理的基类实现<p>
 */
public class IBasePresenterImpl<T extends BaseView> implements IBasePresenter {

    protected Subscription mSubscription;
    protected T mView;

    public IBasePresenterImpl(T view) {
        mView = view;
    }

    @Override
    public void start() {
    }

//    @Override
//    public void onResume() {
//    }
//
//    @Override
//    public void onDestroy() {
//        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
//            mSubscription.unsubscribe();
//        }
//        mView = null;
//    }



}
