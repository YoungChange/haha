package com.hailer.news.contract.model;

import com.hailer.news.util.bean.FeedBackMessage;
import com.hailer.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * Created by moma on 17-7-25.
 */

public class IContractUsInteractorImpl implements IContractUsInteractor{
    @Override
    public Subscription submitMeaaage(RequestCallback callback,FeedBackMessage message) {
        return null;
    }
}
