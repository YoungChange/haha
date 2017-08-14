package com.hailer.news.contract.model;

import com.hailer.news.contract.RequestCallback;

import rx.Subscription;

/**
 * Created by moma on 17-7-25.
 */

public interface IContractUsInteractor<T> {
    Subscription submitMeaaage(RequestCallback<T> callback, String email, String feedback);
}
