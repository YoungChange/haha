package com.hailer.news.news;

import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.base.presenter.BasePresenter;
import com.hailer.news.base.presenter.IBasePresenter;
import com.hailer.news.base.view.BaseView;
import com.hailer.news.util.bean.NewsChannelBean;

import java.util.List;

/**
 * Created by moma on 17-8-1.
 */
public interface NewsContract {
    interface View extends BaseView {
        void showChannels(List<NewsChannelBean> data);

        void showNewsList(int loadType, List<NewsItem> list);

        void upateUserView();

        void showErrorMsg();
    }

    interface Presenter extends IBasePresenter {

        void refreshData(String catId);

        void loadMoreData(String catId, int itemCount);

        void autoLogin();

        void getUserChannel();

        void getNewsList(String catId);

    }
}
