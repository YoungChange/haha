package com.hailer.news.home.model;

import com.hailer.news.api.APIConfig;
import com.hailer.news.api.RetrofitService;
import com.hailer.news.base.BaseSubscriber;
import com.hailer.news.base.presenter.BasePresenterImpl;
import com.hailer.news.home.view.INewsDetailView;
import com.hailer.news.util.bean.NewsComment;
import com.hailer.news.util.callback.RequestCallback;

import rx.Subscription;


public class ISendCommentInteractorImpl implements ISendCommentInteractor<String>{

    @Override
    public Subscription submitComment(RequestCallback callback, String commentContent) {
//        Subscription subscription = RetrofitService.getInstance(APIConfig.HOST_TYPE_NEWS)
//                .postCommentObservable(commentContent)
//                .subscribe(new BaseSubscriber<String>(callback));
//
//        return subscription;

        return null;
    }
}
