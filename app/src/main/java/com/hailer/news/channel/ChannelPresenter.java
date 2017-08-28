package com.hailer.news.channel;

import com.hailer.news.common.RxCallback;
import com.hailer.news.model.LocalDataSource;
import com.hailer.news.model.RemoteDataSource;
import com.hailer.news.util.RxBus;
import com.hailer.news.util.bean.ChannelInfo;
import com.hailer.news.util.bean.NewsChannelBean;

import java.util.List;

/**
 * Created by moma on 17-8-24.
 */

public class ChannelPresenter implements ChannelContract.Presenter{

    private ChannelContract.View mView;
    private LocalDataSource mLocalData;
    private RxCallback mGetUserChannelListCallback;
    private RxCallback mGetOtherChannelCallback;

    private RxCallback mChangeChannelCallback;

    public ChannelPresenter(ChannelContract.View view) {
        mView = view;

        mGetUserChannelListCallback = new RxCallback<List<NewsChannelBean>>() {
            @Override
            public void requestError(int msg) {
            }

            @Override
            public void requestSuccess(List<NewsChannelBean> data) {

            }
        };

        mGetOtherChannelCallback = new RxCallback() {
            @Override
            public void requestError(int msgType) {

            }

            @Override
            public void requestSuccess(Object data) {

            }
        };

        mChangeChannelCallback = new RxCallback() {
            @Override
            public void requestError(int msgType) {

            }

            @Override
            public void requestSuccess(Object data) {

            }
        };

        mLocalData = new LocalDataSource();
    }

    @Override
    public void getUserChannelFromLocal() {
        mLocalData.getUserChannel(mGetUserChannelListCallback);
    }

    @Override
    public void getOtherChannelFromLocal() {
        mLocalData.getOtherChannel(mGetOtherChannelCallback);
    }


    @Override
    public void updateChannel(List<ChannelInfo> list){
        mLocalData.updateChannel(mChangeChannelCallback,list);
    }



    /**
     * UserChannel添加某个channel
     * @param channelName
     */
    @Override
    public void onItemAdd(String channelName) {}

    /**
     * UserChannel移除某个channel
     * @param channelName
     */
    @Override
    public void onItemRemove(String channelName) {}

    /**
     * 交换位置操作
     * @param fromPos
     * @param toPos
     */
    @Override
    public void onItemSwap(int fromPos, int toPos) {}

}
