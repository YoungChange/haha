package com.hailer.news.home.model;


import com.hailer.news.api.APIConfig;
import com.hailer.news.api.RetrofitService;
import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.base.BaseSubscriber;
import com.hailer.news.util.callback.RequestCallback;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;

import com.socks.library.KLog;


/**
 * Fuction: 新闻列表的Model层接口实现<p>
 */

public class INewsListInteractorImpl implements INewsListInteractor<List<NewsItem>> {

    @Override
    public Subscription requestNewsList(final RequestCallback<List<NewsItem>> callback, String type, final String id, int startPage) {
        KLog.e("新闻列表：" + type + ";" + id);

        Subscription subscription = RetrofitService.getInstance(APIConfig.HOST_TYPE_NEWS)
                .getNewsListObservable(id, startPage)
                .flatMap(new Func1<Map<String, List<NewsItem>>, Observable<NewsItem>>() {
                    @Override
                    public Observable<NewsItem> call(Map<String, List<NewsItem>> stringListMap) {
                        return Observable.from(stringListMap.get(APIConfig.NEWS_DATA_JSON_KEY));
                    }
                })
                .toSortedList(new Func2<NewsItem, NewsItem, Integer>() {
                    // 按时间先后排序
                    @Override
                    public Integer call(NewsItem newsSummary, NewsItem newsSummary2) {
                        return newsSummary2.getDate().compareTo(newsSummary.getDate());
                    }
                }).subscribe(new BaseSubscriber<List<NewsItem>>(callback));



        return subscription;

    }
}
