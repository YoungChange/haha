package com.hailer.news.comments;

import com.hailer.news.api.bean.CommentInfo;

import java.util.List;

/**
 * Created by moma on 17-8-1.
 */
public interface CommentsContract {
    interface View {

        void showCommentsList(List<CommentInfo> data,boolean isRefresh);

        void showErrorMsg(int error);
    }

    interface Presenter {

        void refreshData();

        void loadMoreData();

        void getCommentsList(String postId);

    }
}
