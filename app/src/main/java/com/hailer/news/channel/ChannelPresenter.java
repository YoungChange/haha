package com.hailer.news.channel;

import com.hailer.news.common.RxCallback;
import com.hailer.news.model.LocalDataSource;
import com.hailer.news.model.RemoteDataSource;
import com.hailer.news.util.bean.NewsChannelBean;

import java.util.List;

/**
 * Created by moma on 17-8-24.
 */

public class ChannelPresenter implements ChannelContract.Presenter{

    private ChannelContract.View mView;

    private LocalDataSource mLocalData;
    private RemoteDataSource mRemoteData;

    private RxCallback mGetAllChannelCallback;
    private RxCallback mUserGetChannelListCallback;
    private RxCallback mLoginCallback;
    private RxCallback mPostDataCallback;

    public ChannelPresenter(ChannelContract.View view) {
        mView = view;

        mGetAllChannelCallback = new RxCallback<List<NewsChannelBean>>() {
            @Override
            public void requestError(int msg) {
            }

            @Override
            public void requestSuccess(List<NewsChannelBean> data) {

            }
        };

        mUserGetChannelListCallback = new RxCallback() {
            @Override
            public void requestError(int msgType) {

            }

            @Override
            public void requestSuccess(Object data) {

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


        mLocalData = new LocalDataSource();
        mRemoteData = new RemoteDataSource();
    }

    @Override
    public void getUserChannelFromRemote() {
//        mRemoteData.getUserChannel(mUserGetChannelListCallback);
    }

    @Override
    public void getUserChannelFromLocal() {
//        mLocalData.getUserChannel(mUserGetChannelListCallback);
    }

    @Override
    public void getAllChannelFromRemote() {
        mRemoteData.getAllChannel(mGetAllChannelCallback);
    }

    @Override
    public void getAllChannelFromLocal() {
//        mLocalData.getAllChannel(mGetAllChannelCallback);
    }
}
