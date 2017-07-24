package com.moma.app.news.home.model;

import com.moma.app.news.api.RetrofitService;
import com.moma.app.news.api.bean.NewsDetail;
import com.moma.app.news.base.BaseSubscriber;
import com.moma.app.news.util.callback.RequestCallback;

import java.util.Map;

import rx.Subscription;
import rx.functions.Func1;

/**
 * ClassName: INewsDetailInteractorImpl<p>
 * Author: oubowu<p>
 * Fuction: 新闻详情的Model层接口实现<p>
 * CreateDate: 2016/2/19 21:02<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public class INewsDetailInteractorImpl implements INewsDetailInteractor<NewsDetail> {

    @Override
    public Subscription requestNewsDetail(final RequestCallback<NewsDetail> callback, final String id) {
        return RetrofitService.getInstance(1).getNewsDetailObservable(id)
                .map(new Func1<Map<String, NewsDetail>, NewsDetail>() {
                    @Override
                    public NewsDetail call(Map<String, NewsDetail> map) {
                        return map.get("data");
                    }
                }).subscribe(new BaseSubscriber<>(callback));
    }

}
