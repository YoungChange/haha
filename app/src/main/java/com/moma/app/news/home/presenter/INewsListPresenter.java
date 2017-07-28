package com.moma.app.news.home.presenter;


import com.moma.app.news.base.presenter.BasePresenter;

/**
 * Fuction: 新闻列表代理接口<p>
 */
public interface INewsListPresenter extends BasePresenter {

    void refreshData();

    void loadMoreData();

}
