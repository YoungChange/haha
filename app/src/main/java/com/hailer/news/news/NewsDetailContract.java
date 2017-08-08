package com.hailer.news.news;

import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.base.presenter.IBasePresenter;
import com.hailer.news.base.view.BaseView;

/**
 * Created by moma on 17-8-1.
 */
public interface NewsDetailContract {
    interface View extends BaseView {
        void showDetail(NewsDetail newsDetail);

        void showCommentMsg();

        void handleError();

        void popLoginDlg();
    }

    interface Presenter extends IBasePresenter {

        void getDetail(String postId);

        void postComment(String postId, String comment);

        boolean isLoggedIn();

    }
}
