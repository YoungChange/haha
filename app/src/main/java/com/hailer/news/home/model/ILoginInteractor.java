package com.hailer.news.home.model;


import com.hailer.news.util.bean.UserInfo;
import com.hailer.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * Fuction: 新闻详情的Model层接口<p>
 */
public interface ILoginInteractor<T> {

    Subscription login(RequestCallback<T> callback, UserInfo userInfo);

}
