package com.moma.app.news.contract.model;

import com.moma.app.news.util.bean.FeedBackMessage;
import com.moma.app.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * Created by moma on 17-7-25.
 */

public interface IContractUsInteractor<T> {
    Subscription submitMeaaage(RequestCallback<T> callback,FeedBackMessage message);
}
