package com.moma.app.news.home.view;

import android.support.annotation.NonNull;

import com.moma.app.news.api.bean.NewsItem;
import com.moma.app.news.base.DataLoadType;
import com.moma.app.news.base.view.BaseView;

import java.util.List;

/**
 * Fuction: 新闻列表视图接口<p>
 */
public interface INewsListView extends BaseView {

    void updateNewsList(List<NewsItem> data, @NonNull String errorMsg, @DataLoadType.DataLoadTypeChecker int type);

}
