
package com.hailer.news.feedback;

import com.hailer.news.UserManager;
import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.common.ErrMsg;
import com.hailer.news.common.RxCallback;
import com.hailer.news.model.RemoteDataSource;
import com.hailer.news.newsdetail.NewsDetailContract;
import com.hailer.news.util.bean.FeedBackMessage;
import com.socks.library.KLog;

/**
 * Created by moma on 17-7-17.
 */

public class FeedbackPresenter implements FeedbackContract.Presenter {
    private FeedbackContract.View mView;
    private RemoteDataSource mRemoteData;
    private RxCallback mPostDataCallback;

    public FeedbackPresenter(FeedbackContract.View view) {
        mView = view;
        mRemoteData = new RemoteDataSource();

        mPostDataCallback = new RxCallback() {
            @Override
            public void requestError(int msg) {
                KLog.e("requestError, msg="+msg);
                mView.showMsg(msg);
            }

            @Override
            public void requestSuccess(Object data) {
                KLog.e(".........postComment success");
                mView.showMsg(ErrMsg.SUCCESS);
            }
        };
    }

    @Override
    public void postFeedback(FeedBackMessage feedback) {
        //load data
        mRemoteData.postFeedback(feedback.getUserEmail(), feedback.getMessageContent(), mPostDataCallback);
    }

}
