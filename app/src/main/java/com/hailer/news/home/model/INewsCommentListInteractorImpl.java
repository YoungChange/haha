package com.hailer.news.home.model;

import com.hailer.news.api.APIConfig;
import com.hailer.news.api.RetrofitService;
import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.base.BaseSubscriber;
import com.hailer.news.util.bean.NewsComment;
import com.hailer.news.util.callback.RequestCallback;
import com.socks.library.KLog;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Fuction: 新闻评论列表的Model层接口实现<p>
 */

public class INewsCommentListInteractorImpl implements INewsCommentListInteractor<List<NewsComment>>{

    @Override
    public Subscription requestNewsCommentList(RequestCallback<List<NewsComment>> callback, String newsPostId, int startPage) {
        KLog.e("新闻评论postID" + newsPostId);

        return null;

    }
}
