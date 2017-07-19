package com.moma.app.news.home.view;



import com.moma.app.news.base.view.BaseView;
import com.moma.app.news.util.bean.NewsChannelBean;

import java.util.List;

/**
 * ClassName: INewsView<p>
 * Author: oubowu<p>
 * Fuction: 新闻视图接口<p>
 * CreateDate: 2016/2/17 20:25<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public interface INewsView extends BaseView {

    void initViewPager(List<NewsChannelBean> newsChannels);

    void initRxBusEvent();

}
