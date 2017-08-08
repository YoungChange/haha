package com.hailer.news.news;

import com.hailer.news.base.presenter.BasePresenter;
import com.hailer.news.base.presenter.IBasePresenter;
import com.hailer.news.base.view.BaseView;

/**
 * Created by moma on 17-8-1.
 */
public interface NewsContract {
    interface View extends BaseView {
        void showChannels();

        void showNewsList();

        void upateUserView();

        void showErrorMsg();
    }

    interface Presenter extends IBasePresenter {

        void refreshData();

        void loadMoreData();

        void autoLogin();

        void getUserChannel();

        void getNewsList(String catId);

    }
}
