package com.hailer.news.news;

import android.support.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hailer.news.UserManager;
import com.hailer.news.api.APIConfig;
import com.hailer.news.api.RetrofitService;
import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.api.bean.LoginInfo;
import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.base.BaseSubscriber;
import com.hailer.news.base.presenter.BaseDataSource;
import com.hailer.news.base.presenter.BaseModel;
import com.hailer.news.util.bean.UserInfo;
import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.util.callback.RequestCallback;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Fuction: 代理的基类<p>
 */
public class RemoteDataSource extends BaseModel {
    private BaseDataSource.LoginCallback mCallBack;
    private BaseDataSource.GetDataCallback mGetDataCallback;

    protected HashMap<String, Subscription> mSubscriptionMap = new HashMap<>();

    public void onDestroy() {
//        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
//            mSubscription.unsubscribe();
//        }
    }

    public RemoteDataSource(){

    }

//    @Override
//    public void requestComplete() {
//
//    }
//
//    @Override
//    public void requestSuccess(T data) {
//
//    }



    public void login(UserInfo userInfo, RxCallback callback){
        Subscription loginSub = RetrofitService.getInstance(APIConfig.HOST_TYPE_NEWS)
                .loginObservable(userInfo)
                .subscribe(new RemoteSubscriber<LoginInfo>(callback));

    }

    public void getNewsList(String categoryId, int startNum, RxCallback callback) {
        Subscription loginSub = RetrofitService.getInstance(APIConfig.HOST_TYPE_NEWS)
                .getNewsListObservable(categoryId, startNum)
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
                })
                .subscribe(new RemoteSubscriber<List<NewsItem>>(callback));

    }

    public void getNewsDetail(String postId, RxCallback callback) {
        Subscription loginSub = RetrofitService.getInstance(APIConfig.HOST_TYPE_NEWS)
                .getNewsDetailObservable(postId)
                .map(new Func1<Map<String, NewsDetail>, NewsDetail>() {
                    @Override
                    public NewsDetail call(Map<String, NewsDetail> map) {
                        return map.get(APIConfig.NEWS_DATA_JSON_KEY);
                    }
                })
                .subscribe(new RemoteSubscriber<NewsDetail>(callback));

    }

    public void getCommentsList(String postId, int startNum, RxCallback callback) {
        Subscription loginSub = RetrofitService.getInstance(APIConfig.HOST_TYPE_NEWS)
                .getCommentsListObservable(postId, startNum)
                .flatMap(new Func1<Map<String, List<CommentInfo>>, Observable<CommentInfo>>() {
                    @Override
                    public Observable<CommentInfo> call(Map<String, List<CommentInfo>> stringListMap) {
                        return Observable.from(stringListMap.get(APIConfig.NEWS_DATA_JSON_KEY));
                    }
                })
                .toSortedList(new Func2<CommentInfo, CommentInfo, Integer>() {
                    // 按时间先后排序
                    @Override
                    public Integer call(CommentInfo newsComment, CommentInfo newsComment2) {
                        return newsComment2.getDate().compareTo(newsComment.getDate());
                    }
                })
                .subscribe(new RemoteSubscriber<List<CommentInfo>>(callback));

    }

    public void postComment(String postId, String serverToken, String comment, RxCallback callback) {
        Subscription loginSub = RetrofitService.getInstance(APIConfig.HOST_TYPE_NEWS)
                .postCommentObservable(postId, serverToken, comment)
                .subscribe(new RemoteSubscriber<String>(callback));

    }

    public void postFeedback(String email, String feedback, RxCallback callback) {
        Subscription loginSub = RetrofitService.getInstance(APIConfig.HOST_TYPE_NEWS)
                .postFeedbackObservable(email, feedback)
                .subscribe(new RemoteSubscriber<String>(callback));

    }

}
