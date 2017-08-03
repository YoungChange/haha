package com.hailer.news.home.model;

import com.hailer.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * Created by moma on 17-8-3.
 */

public interface ISendCommentInteractor<T> {
    Subscription submitComment(RequestCallback<T> callback, String commentContent);
}
