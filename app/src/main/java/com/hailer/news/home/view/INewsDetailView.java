package com.hailer.news.home.view;


import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.base.view.BaseView;

/**
 * Fuction: 新闻详情视图接口<p>
 */
public interface INewsDetailView extends BaseView {

    void initNewsDetail(NewsDetail data);

}
