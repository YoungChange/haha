package com.hailer.news.home.presenter;

import com.hailer.news.base.presenter.BasePresenter;

/**
 * Created by moma on 17-8-1.
 */
public interface ICommentsListPresenter extends BasePresenter{

    void refreshData();
    void loadMoreData();
}
