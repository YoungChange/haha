package com.hailer.news.newsdetailandcomment;

import com.hailer.news.NewsApplication;
import com.hailer.news.UserManager;
import com.hailer.news.api.APIConfig;
import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.comments.CommentsContract;
import com.hailer.news.common.ErrMsg;
import com.hailer.news.common.RxCallback;
import com.hailer.news.model.RemoteDataSource;
import com.hailer.news.util.CommentVoteUtil;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by moma on 17-8-23.
 */

public class NewsDetailAddCommentPresenter implements NewsDetailAddCommentContract.Presenter{

    private NewsDetailAddCommentContract.View mView;
    private RemoteDataSource mRemoteData;

    private int mStartPage = 0;
    private String mPostId;
    private boolean mIsRefresh = true;

    private RxCallback mGetCommentListCallback;
    private RxCallback mLoginCallback;
    private RxCallback mPostCommentCallback;
    private RxCallback mGetNewsDetailCallback;


    public NewsDetailAddCommentPresenter(NewsDetailAddCommentContract.View view) {
        mView = view;
        mRemoteData = new RemoteDataSource();

        mGetCommentListCallback = new RxCallback<List<CommentInfo>>() {
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

        mPostCommentCallback = new RxCallback() {
            @Override
            public void requestError(int msg) {
                KLog.e("-NewsDetailPresenter------mPostDataCallback--requestError error msg ="+ msg);
            }

            @Override
            public void requestSuccess(Object data) {
                KLog.e("--NewsDetailPresenter------mPostDataCallback---requestSucess...");

                mView.showCommentMsg();
            }
        };

        mGetNewsDetailCallback = new RxCallback<NewsDetail>() {
            @Override
            public void requestError(int msg) {
                mView.handleError();
            }

            @Override
            public void requestSuccess(NewsDetail data) {
                mView.showDetail(data);
            }
        };

    }



    @Override
    public void getNewsDetail(String postId) {
        mRemoteData.getNewsDetail(postId, mGetNewsDetailCallback);
    }

    @Override
    public boolean isLoggedIn() {

        String token = UserManager.getInstance().getServerToken();
        if (token == null || token.isEmpty()){
            return false;
        }

        return true;
    }

    @Override
    public void refreshData() {
        KLog.i("refreshData()............");
        mStartPage = 0;
        mIsRefresh = true;
        if(mPostId!=null){
            mRemoteData.getCommentsList(mPostId, mStartPage, mGetCommentListCallback);
        }
    }

    @Override
    public void loadMoreData() {
        mIsRefresh = false;
        if(mPostId!=null){
            mRemoteData.getCommentsList(mPostId, mStartPage, mGetCommentListCallback);
        }
    }

    @Override
    public void getCommentsList(String postId) {
        mPostId = postId;
        mRemoteData.getCommentsList(postId, mStartPage, mGetCommentListCallback);
    }

    @Override
    public void voteComment(final CommentInfo commentInfo) {
        String token = UserManager.getInstance().getServerToken();
        if (token != null && !token.isEmpty() && commentInfo != null) {
            mRemoteData.postVote(Integer.toString(commentInfo.getId()), token, new RxCallback() {
                @Override
                public void requestError(int msgType) {
                    mView.resetVote();
                }

                @Override
                public void requestSuccess(Object data) {

                }
            });
        }
        //mView.popLoginDlg();
        // 先不登录，保存数据到本地
        CommentVoteUtil.getInstances(NewsApplication.getContext()).setVoted(commentInfo.getId(), true);
    }

    @Override
    public void postComment(String postId, String comment) {
        String token = UserManager.getInstance().getServerToken();
        if (token != null && !token.isEmpty()) {
            mRemoteData.postComment(postId, token, comment, mPostCommentCallback);
        } else {
            mView.popLoginDlg();
        }
    }
}
