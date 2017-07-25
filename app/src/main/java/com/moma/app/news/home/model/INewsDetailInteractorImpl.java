package com.moma.app.news.home.model;

import com.moma.app.news.api.APIConfig;
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
        return RetrofitService.getInstance(APIConfig.HOST_TYPE_NEWS).getNewsDetailObservable(id)
                .map(new Func1<Map<String, NewsDetail>, NewsDetail>() {
                    @Override
                    public NewsDetail call(Map<String, NewsDetail> map) {
                        return map.get(APIConfig.NEWS_DATA_JSON_KEY);
                    }
                }).subscribe(new BaseSubscriber<>(callback));
    }

}
