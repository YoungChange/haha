package com.hailer.news.comments;

import com.hailer.news.api.APIConfig;
import com.hailer.news.common.ErrMsg;
import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.model.RemoteDataSource;
import com.hailer.news.common.RxCallback;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by moma on 17-7-17.
 */

public class CommentsPresenter implements CommentsContract.Presenter {
    private CommentsContract.View mView;
    private RemoteDataSource mRemoteData;
    private int mStartPage = 0;
    private RxCallback mGetDataCallback;
    private RxCallback mLoginCallback;
    private RxCallback mPostDataCallback;

    public CommentsPresenter(CommentsContract.View view) {
        mView = view;
        mRemoteData = new RemoteDataSource();

        mGetDataCallback = new RxCallback<List<CommentInfo>>() {
            @Override
            public void requestError(int msg) {
                int error = ErrMsg.LOAD_DATA_ERROR;
                mView.showErrorMsg(error);
            }

            @Override
            public void requestSuccess(List<CommentInfo> data) {
                mStartPage += APIConfig.LIST_ITEMS_PER_PAGE;
                mView.showCommentsList(data);
            }
        };

    }

    @Override
    public void start() {
        //load data
        //mDataSource.getNewsList(categoryId, mStartPage);
    }

    @Override
    public void getCommentsList(String postId) {
        //load data
        mRemoteData.getCommentsList(postId, mStartPage, mGetDataCallback);
    }

    @Override
    public void refreshData() {
        KLog.i("refreshData()............");
        mStartPage = 1;
        //refresh
        mRemoteData.getCommentsList("", mStartPage, mGetDataCallback);
    }

    @Override
    public void loadMoreData() {
        //load data
        mRemoteData.getCommentsList("", mStartPage, mGetDataCallback);
    }

}
