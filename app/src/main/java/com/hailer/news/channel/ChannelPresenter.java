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

    private LocalDataSource mLocalAllData;
    private LocalDataSource mLocalUserData;
    private RemoteDataSource mRemoteAllData;
    private RemoteDataSource mRemoteUserData;

    private RxCallback mAllGetChannelListCallback;
    private RxCallback mUserGetChannelListCallback;
    private RxCallback mLoginCallback;
    private RxCallback mPostDataCallback;

    public ChannelPresenter(ChannelContract.View view) {
        mView = view;

        mAllGetChannelListCallback = new RxCallback<List<NewsChannelBean>>() {
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


        mLocalAllData = new LocalDataSource(mAllGetChannelListCallback);
        mLocalUserData = new LocalDataSource(mUserGetChannelListCallback);

    }




}
