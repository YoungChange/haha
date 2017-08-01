package com.hailer.news.home.model;

import com.hailer.news.util.callback.RequestCallback;

import rx.Subscription;

/**
 * Fuction: 新闻评论Model层接口<p>
 */

public interface INewsCommentListInteractor<T> {
    Subscription requestNewsCommentList(RequestCallback<T> callback, String newsPostId, int startPage);
}
