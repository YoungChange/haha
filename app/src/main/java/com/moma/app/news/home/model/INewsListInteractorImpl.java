package com.moma.app.news.home.model;


import com.moma.app.news.api.RetrofitService;
import com.moma.app.news.api.bean.NewsItem;
import com.moma.app.news.base.BaseSubscriber;
import com.moma.app.news.util.callback.RequestCallback;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;
import com.socks.library.KLog;

public class INewsListInteractorImpl implements INewsListInteractor<List<NewsItem>> {

//    RetrofitService.getInstance(1).getNewsListObservable(type, id, startPage)

    @Override
    public Subscription requestNewsList(final RequestCallback<List<NewsItem>> callback, String type, final String id, int startPage) {
        KLog.e("新闻列表：" + type + ";" + id);

        Subscription subscription = RetrofitService.getInstance(1)
                .getNewsListObservable(type, id, startPage)
                .flatMap(new Func1<Map<String, List<NewsItem>>, Observable<NewsItem>>() {
                    @Override
                    public Observable<NewsItem> call(Map<String, List<NewsItem>> stringListMap) {
                        KLog.e("......bailei....id="+id);
                        for (String key : stringListMap.keySet()) {
                            KLog.e("key= " + key + " and value= " + stringListMap.get(key));
                        }
                        String key1 = "data";
                        return Observable.from(stringListMap.get(key1));
                    }
                })
                //bailei
                .toSortedList(new Func2<NewsItem, NewsItem, Integer>() {
                    // 按时间先后排序
                    @Override
                    public Integer call(NewsItem newsSummary, NewsItem newsSummary2) {
                        return newsSummary2.post_date.compareTo(newsSummary.post_date);
                    }
                }).subscribe(new BaseSubscriber<List<NewsItem>>(callback));



        return subscription;

    }
}
