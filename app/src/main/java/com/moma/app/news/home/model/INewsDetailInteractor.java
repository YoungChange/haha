package com.moma.app.news.home.model;


import com.moma.app.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * Fuction: 新闻详情的Model层接口<p>
 */
public interface INewsDetailInteractor<T> {

    Subscription requestNewsDetail(RequestCallback<T> callback, String id);

}
