package com.hailer.news.news;

import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.base.ErrMsg;
import com.hailer.news.base.presenter.IBasePresenter;
import com.hailer.news.base.view.BaseView;

import java.util.List;

/**
 * Created by moma on 17-8-1.
 */
public interface CommentsContract {
    interface View extends BaseView {

        void showCommentsList(List<CommentInfo> data);

        void showErrorMsg(int error);
    }

    interface Presenter extends IBasePresenter {

        void refreshData();

        void loadMoreData();

        void getCommentsList(String postId);

    }
}
