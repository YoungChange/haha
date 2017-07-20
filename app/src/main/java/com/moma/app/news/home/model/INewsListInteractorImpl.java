package com.moma.app.news.home.model;


import com.moma.app.news.api.RetrofitService;
import com.moma.app.news.api.bean.NewsList;
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

public class INewsListInteractorImpl implements INewsListInteractor<List<NewsList>> {

//    RetrofitService.getInstance(1).getNewsListObservable(type, id, startPage)

    @Override
    public Subscription requestNewsList(final RequestCallback<List<NewsList>> callback, String type, final String id, int startPage) {
//        KLog.e("新闻列表：" + type + ";" + id);

        type = "list";

        Subscription subscription = RetrofitService.getInstance(1)
                .getNewsListObservable(type, id, startPage)
                .flatMap(new Func1<Map<String, List<NewsList>>, Observable<NewsList>>() {
                    @Override
                    public Observable<NewsList> call(Map<String, List<NewsList>> stringListMap) {
                        return Observable.from(stringListMap.get(id));
                    }
                })
                .toSortedList(new Func2<NewsList, NewsList, Integer>() {
                    // 按时间先后排序
                    @Override
                    public Integer call(NewsList neteastNewsSummary, NewsList neteastNewsSummary2) {
                        return neteastNewsSummary2.ptime.compareTo(neteastNewsSummary.ptime);
                    }
                }).subscribe(new BaseSubscriber<List<NewsList>>(callback));



        return subscription;

    }
}
