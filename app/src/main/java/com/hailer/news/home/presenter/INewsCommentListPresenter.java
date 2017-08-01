package com.hailer.news.home.presenter;

import com.hailer.news.base.presenter.BasePresenter;

/**
 * Created by moma on 17-8-1.
 */
public interface INewsCommentListPresenter extends BasePresenter{

    void refreshData();
    void loadMoreData();
}
