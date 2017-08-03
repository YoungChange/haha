package com.hailer.news.home.model;

import com.hailer.news.api.APIConfig;
import com.hailer.news.api.RetrofitService;
import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.base.BaseSubscriber;
import com.hailer.news.util.bean.NewsChannelBean;
import com.hailer.news.util.bean.NewsComment;
import com.hailer.news.util.callback.RequestCallback;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Fuction: 新闻评论列表的Model层接口实现<p>
 */

public class INewsCommentListInteractorImpl implements INewsCommentListInteractor<List<NewsComment>>{

    @Override
    public Subscription requestNewsCommentList(final RequestCallback<List<NewsComment>> callback,final String newsPostId,final int startPage) {
        KLog.e("新闻评论postID" + newsPostId);

//        Subscription subscription = RetrofitService.getInstance(APIConfig.HOST_TYPE_NEWS)
//                .getNewsCommentListObservable(newsPostId, startPage)
//                .flatMap(new Func1<Map<String, List<NewsComment>>, Observable<NewsComment>>() {
//                    @Override
//                    public Observable<NewsComment> call(Map<String, List<NewsComment>> stringListMap) {
//                        return Observable.from(stringListMap.get(APIConfig.NEWS_DATA_JSON_KEY));
//                    }
//                })
//                .toSortedList(new Func2<NewsComment, NewsComment, Integer>() {
//                    // 按时间先后排序
//                    @Override
//                    public Integer call(NewsComment newsComment, NewsComment newsComment2) {
//                        return newsComment2.post_date.compareTo(newsComment.post_date);
//                    }
//                }).subscribe(new BaseSubscriber<List<NewsComment>>(callback));
//        return subscription;




        return Observable.create(new Observable.OnSubscribe<List<NewsComment>>() {
            @Override
            public void call(Subscriber<? super List<NewsComment>> subscriber) {

                String picUrl = "https://fb-s-a-a.akamaihd.net/h-ak-fbx/v/t1.0-1/c17.0.100.100/p100x100/10612763_1515886005293288_3012227666684371957_n.jpg?oh=112351a1b07323c0e01e9532d8718d60&oe=59FAE3AA&__gda__=1509400699_00c873ab9bce8f51ea6757bfd3efd40a";

                NewsComment bean1 = new NewsComment("2017-9-8 8:8:8","这个问题困扰了很久，看到这个解决了。",startPage+"极客大集合"+newsPostId,picUrl);
                NewsComment bean2 = new NewsComment("2017-9-8 8:8:8","这个问题困扰了很久，看到这个解决了。",startPage+"极客大集合"+newsPostId,picUrl);

                List<NewsComment> newsCommentList = new ArrayList<NewsComment>();
                newsCommentList.add(bean1);

                newsCommentList.add(bean2);

                subscriber.onNext(newsCommentList);
                subscriber.onCompleted();


            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callback.beforeRequest();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<NewsComment>>() {
                    @Override
                    public void onCompleted() {
                        callback.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.requestError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(List<NewsComment> newsCommentList) {
                        callback.requestSuccess(newsCommentList);
                    }
                });

    }
}
