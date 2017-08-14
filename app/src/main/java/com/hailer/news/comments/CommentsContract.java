package com.hailer.news.comments;

import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.contract.IBasePresenter;

import java.util.List;

/**
 * Created by moma on 17-8-1.
 */
public interface CommentsContract {
    interface View {

        void showCommentsList(List<CommentInfo> data);

        void showErrorMsg(int error);
    }

    interface Presenter extends IBasePresenter {

        void refreshData();

        void loadMoreData();

        void getCommentsList(String postId);

    }
}
