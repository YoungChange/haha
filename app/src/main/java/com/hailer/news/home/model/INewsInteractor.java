package com.hailer.news.home.model;

import com.hailer.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * Fuction: 新闻Model层接口<p>
 */
public interface INewsInteractor<T> {

    Subscription operateChannel(RequestCallback<T> callback);

}
