package com.hailer.news.contract;


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
