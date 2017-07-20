package com.moma.app.news.home.view;

import android.support.annotation.NonNull;

import com.moma.app.news.api.bean.NewsList;
import com.moma.app.news.base.DataLoadType;
import com.moma.app.news.base.view.BaseView;

import java.util.List;

/**
 * ClassName: INewsListView<p>
 * Author: oubowu<p>
 * Fuction: 新闻列表视图接口<p>
 * CreateDate: 2016/2/18 14:42<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public interface INewsListView extends BaseView {

    void updateNewsList(List<NewsList> data, @NonNull String errorMsg, @DataLoadType.DataLoadTypeChecker int type);

}
