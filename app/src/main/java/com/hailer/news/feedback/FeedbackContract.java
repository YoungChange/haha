package com.hailer.news.feedback;

import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.util.bean.FeedBackMessage;

/**
 * Created by moma on 17-8-1.
 */
public interface FeedbackContract {
    interface View {
        void showMsg(int msg);
    }

    interface Presenter {

        void postFeedback(FeedBackMessage feedback);

    }
}
