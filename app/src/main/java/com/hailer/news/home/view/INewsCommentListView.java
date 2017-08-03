package com.hailer.news.home.view;

import android.support.annotation.NonNull;

import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.base.DataLoadType;
import com.hailer.news.base.view.BaseView;
import com.hailer.news.util.bean.NewsComment;

import java.util.List;

/**
 * Created by moma on 17-8-1.
 */

public interface INewsCommentListView extends BaseView {
    void updateNewsCommentList(List<CommentInfo> data, @NonNull String errorMsg, @DataLoadType.DataLoadTypeChecker int type);
}
