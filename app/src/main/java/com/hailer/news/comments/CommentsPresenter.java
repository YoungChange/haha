package com.hailer.news.comments;

import com.hailer.news.NewsApplication;
import com.hailer.news.UserManager;
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
    private String mPostId;
    private boolean mIsRefresh = true;

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
                KLog.e("Data.size():"+data.size());
                mStartPage += APIConfig.LIST_ITEMS_PER_PAGE;
                mView.showCommentsList(data,mIsRefresh);
            }
        };

    }

    @Override
    public void getCommentsList(String postId) {
        mPostId = postId;
        //load data
        mRemoteData.getCommentsList(postId, mStartPage, mGetDataCallback);
    }

    @Override
    public void voteComment(int commentId) {
        String token = UserManager.getInstance().getServerToken();
        if (token != null && !token.isEmpty()) {
            //mRemoteData.postComment(postId, token, comment, mPostDataCallback);
            mRemoteData.postVote(Integer.toString(commentId), token, new RxCallback() {
                @Override
                public void requestError(int msgType) {

                }

                @Override
                public void requestSuccess(Object data) {

                }
            });
        } else {
            //提示登录
            mView.popLoginDlg();
        }
    }

    @Override
    public void refreshData() {
        KLog.i("refreshData()............");
        mStartPage = 0;
        mIsRefresh = true;
        if(mPostId!=null){
            mRemoteData.getCommentsList(mPostId, mStartPage, mGetDataCallback);
        }
    }

    @Override
    public void loadMoreData() {
        mIsRefresh = false;
        if(mPostId!=null){
            mRemoteData.getCommentsList(mPostId, mStartPage, mGetDataCallback);
        }
    }

    public void unVoteComment(int commentId) {
        String token = UserManager.getInstance().getServerToken();
        if (token != null && !token.isEmpty()) {
            mRemoteData.postVote(Integer.toString(commentId), token, new RxCallback() {
                @Override
                public void requestError(int msgType) {

                }

                @Override
                public void requestSuccess(Object data) {

                }
            });
        } else {
            //提示登录
            mView.popLoginDlg();
        }
    }
}
