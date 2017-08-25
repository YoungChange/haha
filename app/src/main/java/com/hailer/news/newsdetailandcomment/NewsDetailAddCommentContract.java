package com.hailer.news.newsdetailandcomment;

import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.api.bean.NewsDetail;

import java.io.Serializable;
import java.util.List;

/**
 * Created by moma on 17-8-23.
 */

public class NewsDetailAddCommentContract {
    interface View{
        //NewsDetail
        void showDetail(NewsDetail newsDetail);

        void handleError();

        //NewsComment
        void showCommentsList(List<CommentInfo> data, boolean isRefresh);

        void showErrorMsg(int error);

        void resetVote();

        // 共有
        void popLoginDlg();

        void showCommentMsg();
    }

    interface Presenter{

        //NewsDetail
        void getNewsDetail(String postId);

        boolean isLoggedIn();


        //Comment
        void refreshData();

        void loadMoreData();

        void getCommentsList(String postId);

        void voteComment(CommentInfo info);

        //共有
        void postComment(String postId, String comment);


    }
}
