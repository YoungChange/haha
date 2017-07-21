package com.moma.app.news.home.view;


import com.moma.app.news.api.bean.NewsInfo;
import com.moma.app.news.base.view.BaseView;

/**
 * ClassName: INewsDetailView<p>
 * Author: oubowu<p>
 * Fuction: 新闻详情视图接口<p>
 * CreateDate: 2016/2/19 14:52<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public interface INewsDetailView extends BaseView {

    void initNewsDetail(NewsInfo data);

}
