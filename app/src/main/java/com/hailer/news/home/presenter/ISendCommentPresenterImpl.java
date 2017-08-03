package com.hailer.news.home.presenter;

import com.hailer.news.base.presenter.BasePresenterImpl;
import com.hailer.news.home.view.INewsDetailView;
import com.hailer.news.util.bean.NewsComment;

/**
 * Created by moma on 17-8-2.
 */

public class ISendCommentPresenterImpl extends BasePresenterImpl<INewsDetailView,NewsComment> implements ISendCommentPresenter{
    public ISendCommentPresenterImpl(INewsDetailView view,String commentContent) {
        super(view);
    }


}
