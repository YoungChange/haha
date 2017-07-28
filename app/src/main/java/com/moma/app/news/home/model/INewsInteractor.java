package com.moma.app.news.home.model;

import com.moma.app.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * Fuction: 新闻Model层接口<p>
 */
public interface INewsInteractor<T> {

    Subscription operateChannel(RequestCallback<T> callback);

}
