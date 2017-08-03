package com.hailer.news.home.presenter;

import com.hailer.news.base.presenter.BasePresenterImpl;
import com.hailer.news.home.model.ISendCommentInteractor;
import com.hailer.news.home.model.ISendCommentInteractorImpl;
import com.hailer.news.home.view.INewsDetailView;
import com.hailer.news.util.bean.NewsComment;



public class ISendCommentPresenterImpl extends BasePresenterImpl<INewsDetailView,String> implements ISendCommentPresenter{

    ISendCommentInteractor<String> mSendCommentInteractor;

    public ISendCommentPresenterImpl(INewsDetailView view,String postId, String token, String commentContent) {
        super(view);
        mSendCommentInteractor = new ISendCommentInteractorImpl();
        mSendCommentInteractor.submitComment(this,postId, token, commentContent);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
