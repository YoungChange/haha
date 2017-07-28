package com.hailer.news.home.view;



import com.hailer.news.base.view.BaseView;
import com.hailer.news.util.bean.NewsChannelBean;

import java.util.List;

/**
 * Fuction: 新闻视图接口<p>
 */
public interface INewsView extends BaseView {

    void initViewPager(List<NewsChannelBean> newsChannels);

    void initRxBusEvent();

}
