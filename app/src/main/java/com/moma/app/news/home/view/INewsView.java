package com.moma.app.news.home.view;



import com.moma.app.news.base.view.BaseView;
import com.moma.app.news.util.bean.NewsChannelBean;

import java.util.List;

/**
 * Fuction: 新闻视图接口<p>
 */
public interface INewsView extends BaseView {

    void initViewPager(List<NewsChannelBean> newsChannels);

    void initRxBusEvent();

}
