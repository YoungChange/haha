package com.moma.app.news.home.presenter;

import com.moma.app.news.base.presenter.BasePresenter;

/**
 * Created by moma on 17-7-17.
 */

public interface INewsPresenter extends BasePresenter {
    /**
     * 频道排序或增删变化后调用此方法更新数据库
     */
    void operateChannel();
}
