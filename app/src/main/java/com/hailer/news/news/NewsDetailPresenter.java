
package com.hailer.news.news;

import com.hailer.news.UserManager;
import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.base.presenter.DataCallback;
import com.socks.library.KLog;

/**
 * Created by moma on 17-7-17.
 */

public class NewsDetailPresenter implements NewsDetailContract.Presenter {
    private NewsDetailContract.View mView;
    private RemoteDataSource mRemoteData;
    private DataCallback mDataCallback;
    private RxCallback mGetDataCallback;
    private RxCallback mLoginCallback;
    private RxCallback mPostDataCallback;

    public NewsDetailPresenter(NewsDetailContract.View view) {
        mView = view;
        mRemoteData = new RemoteDataSource();

        mGetDataCallback = new RxCallback<NewsDetail>() {
            @Override
            public void requestError(int msg) {
                mView.handleError();
            }

            @Override
            public void requestSuccess(NewsDetail data) {
                mView.showDetail(data);
            }
        };

        mPostDataCallback = new RxCallback() {
            @Override
            public void requestError(int msg) {
                KLog.e("bailei.........postComment error");
                //mView.showErrorMsg();
                mView.showCommentMsg();
            }

            @Override
            public void requestSuccess(Object data) {
                //ToDo save serverToken
                KLog.e("bailei.........postComment success");

                mView.showCommentMsg();
            }
        };
    }

    @Override
    public void start() {
        //load data
        //mDataSource.getNewsList(categoryId, mStartPage);
    }

    @Override
    public void getDetail(String postId) {
        //load data
        mRemoteData.getNewsDetail(postId, mGetDataCallback);
    }

    @Override
    public boolean isLoggedIn() {
        //load data
        String token = UserManager.getInstance().getServerToken();
        if (token == null || token.isEmpty()){
            return false;
        }

        return true;
    }

    @Override
    public void postComment(String postId, String comment) {
        String token = UserManager.getInstance().getServerToken();
        if (token != null && !token.isEmpty()) {
            mRemoteData.postComment(postId, token, comment, mPostDataCallback);
        } else {
            //提示登录
            mView.popLoginDlg();
        }
    }

}
