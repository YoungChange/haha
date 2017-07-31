package com.hailer.news.contract.model;

import com.hailer.news.api.APIConfig;
import com.hailer.news.api.RetrofitService;
import com.hailer.news.base.BaseSubscriber;
import com.hailer.news.util.bean.FeedBackMessage;
import com.hailer.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * Created by moma on 17-7-25.
 */

public class IContractUsInteractorImpl implements IContractUsInteractor{
    @Override
    public Subscription submitMeaaage(RequestCallback callback, String email, String feedback) {


        Subscription subscription = RetrofitService.getInstance(APIConfig.HOST_TYPE_NEWS)
                .postFeedbackObservable(email, feedback)
                .subscribe(new BaseSubscriber<String>(callback));

        return subscription;
    }
}