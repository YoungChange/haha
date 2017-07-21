package com.moma.app.news.home.model;


import com.moma.app.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * ClassName: INewsDetailInteractor<p>
 * Author: oubowu<p>
 * Fuction: 新闻详情的Model层接口<p>
 * CreateDate: 2016/2/19 21:02<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public interface INewsDetailInteractor<T> {

    Subscription requestNewsDetail(RequestCallback<T> callback, String id);

}
