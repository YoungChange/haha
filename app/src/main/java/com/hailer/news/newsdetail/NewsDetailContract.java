package com.hailer.news.newsdetail;

import com.hailer.news.api.bean.NewsDetail;

/**
 * Created by moma on 17-8-1.
 */
public interface NewsDetailContract {
//        void getNewsInfoSuccess();
//        void getNewsInfoError();
//        void postCommentSuccess();
//        void postCommentError();

    interface View {
        void showDetail(NewsDetail newsDetail);

        void showCommentMsg();

        void handleError();

        void popLoginDlg();
    }

    interface Presenter {

        void getDetail(String postId);

        void postComment(String postId, String comment);

        boolean isLoggedIn();
    }
}
