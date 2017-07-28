package com.hailer.news.home.presenter;


import com.hailer.news.base.presenter.BasePresenter;

/**
 * Fuction: 新闻列表代理接口<p>
 */
public interface INewsListPresenter extends BasePresenter {

    void refreshData();

    void loadMoreData();

}
