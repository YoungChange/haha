package com.hailer.news.home.model;



import com.hailer.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * Fuction: 新闻列表Model层接口<p>
 */
public interface INewsListInteractor<T> {

    Subscription requestNewsList(RequestCallback<T> callback, String type, String id, int startPage);

}
