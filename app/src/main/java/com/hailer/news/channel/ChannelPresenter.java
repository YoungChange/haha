package com.hailer.news.channel;

import com.hailer.news.common.RxCallback;
import com.hailer.news.model.RemoteDataSource;
import com.hailer.news.util.bean.NewsChannelBean;

import java.util.List;

/**
 * Created by moma on 17-8-24.
 */

public class ChannelPresenter implements ChannelContract.Presenter{

    private ChannelContract.View mView;
    private RemoteDataSource mRemoteData;

    private RxCallback mGetChannelListCallback;
    private RxCallback mLoginCallback;
    private RxCallback mPostDataCallback;

    public ChannelPresenter(ChannelContract.View view) {
        mView = view;
        mRemoteData = new RemoteDataSource();

        mGetChannelListCallback = new RxCallback<List<NewsChannelBean>>() {
            @Override
            public void requestError(int msg) {
            }

            @Override
            public void requestSuccess(List<NewsChannelBean> data) {

            }
        };

        mPostDataCallback = new RxCallback() {
            @Override
            public void requestError(int msg) {

            }

            @Override
            public void requestSuccess(Object data) {

            }
        };

    }
}
