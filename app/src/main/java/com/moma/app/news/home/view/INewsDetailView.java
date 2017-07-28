package com.moma.app.news.home.view;


import com.moma.app.news.api.bean.NewsDetail;
import com.moma.app.news.base.view.BaseView;

/**
 * Fuction: 新闻详情视图接口<p>
 */
public interface INewsDetailView extends BaseView {

    void initNewsDetail(NewsDetail data);

}
