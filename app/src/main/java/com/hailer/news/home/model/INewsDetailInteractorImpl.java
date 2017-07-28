package com.hailer.news.home.model;

import com.hailer.news.api.APIConfig;
import com.hailer.news.api.RetrofitService;
import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.base.BaseSubscriber;
import com.hailer.news.util.callback.RequestCallback;

import java.util.Map;

import rx.Subscription;
import rx.functions.Func1;

/**
 * Fuction: 新闻详情的Model层接口实现<p>
 */
public class INewsDetailInteractorImpl implements INewsDetailInteractor<NewsDetail> {

    @Override
    public Subscription requestNewsDetail(final RequestCallback<NewsDetail> callback, final String id) {
        return RetrofitService.getInstance(APIConfig.HOST_TYPE_NEWS).getNewsDetailObservable(id)
                .map(new Func1<Map<String, NewsDetail>, NewsDetail>() {
                    @Override
                    public NewsDetail call(Map<String, NewsDetail> map) {
                        return map.get(APIConfig.NEWS_DATA_JSON_KEY);
                    }
                }).subscribe(new BaseSubscriber<>(callback));
    }

}
