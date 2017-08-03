package com.hailer.news.home.presenter;

import com.hailer.news.api.APIConfig;
import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.base.DataLoadType;
import com.hailer.news.base.presenter.BasePresenterImpl;
import com.hailer.news.home.model.ICommentsListInteractor;
import com.hailer.news.home.model.ICommentsListInteractorImpl;
import com.hailer.news.home.view.INewsCommentListView;
import com.hailer.news.util.bean.NewsComment;

import java.util.List;

/**
 * 新闻评论列表
 */

public class ICommentsListPresenterImpl extends BasePresenterImpl<INewsCommentListView,List<CommentInfo>> implements ICommentsListPresenter {

    private ICommentsListInteractor<List<CommentInfo>> mNewsCommentListInteractor;

    private int mStartPage = 0;
    private String mNewsPostId;

    private boolean mIsRefresh = true;
    private boolean mHasInit;

    public ICommentsListPresenterImpl(INewsCommentListView newsCommentListView, String newsPostId) {
        super(newsCommentListView);
        mNewsCommentListInteractor = new ICommentsListInteractorImpl();
        mSubscription = mNewsCommentListInteractor.requestNewsCommentList(this, newsPostId, mStartPage);
        mNewsPostId = newsPostId;
    }

    @Override
    public void beforeRequest() {
        if (!mHasInit) {
            mHasInit = true;
            mView.showProgress();
        }
    }

    @Override
    public void requestError(String e) {
        super.requestError(e);
        mView.updateNewsCommentList(null, e, mIsRefresh ? DataLoadType.TYPE_REFRESH_FAIL : DataLoadType.TYPE_LOAD_MORE_FAIL);
    }

    @Override
    public void requestSuccess(List<CommentInfo> data) {
        if (data != null) {
            mStartPage += APIConfig.LIST_ITEMS_PER_PAGE;
        }

        mView.updateNewsCommentList(data, "", mIsRefresh ? DataLoadType.TYPE_REFRESH_SUCCESS : DataLoadType.TYPE_LOAD_MORE_SUCCESS);
    }



    @Override
    public void refreshData() {
        mStartPage = 1;
        mIsRefresh = true;
        mSubscription = mNewsCommentListInteractor.requestNewsCommentList(this, mNewsPostId, mStartPage);
    }

    @Override
    public void loadMoreData() {
        mIsRefresh = false;
        mSubscription = mNewsCommentListInteractor.requestNewsCommentList(this, mNewsPostId, mStartPage);
    }
}