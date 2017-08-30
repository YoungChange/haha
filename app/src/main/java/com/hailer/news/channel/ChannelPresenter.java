package com.hailer.news.channel;

import android.util.ArrayMap;

import com.hailer.news.common.Const;
import com.hailer.news.common.RxCallback;
import com.hailer.news.model.LocalDataSource;
import com.hailer.news.util.bean.ChannelInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moma on 17-8-24.
 */

public class ChannelPresenter implements ChannelContract.Presenter{

    private ChannelContract.View mView;
    private LocalDataSource mLocalData;
    private RxCallback mGetUserChannelListCallback;;

    private RxCallback mChangeChannelCallback;

    public ChannelPresenter(final ChannelContract.View view) {
        mView = view;

        mGetUserChannelListCallback = new RxCallback<List<ChannelInfo>>() {
            @Override
            public void requestError(int msg) {
            }

            @Override
            public void requestSuccess(List<ChannelInfo> data) {
                //mView.showChannel(data);
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
    public void getChannels() {
        mLocalData.getChannels(new RxCallback<ArrayMap<String, List>>() {
            @Override
            public void requestError(int msgType) {
            }

            @Override
            public void requestSuccess(ArrayMap<String, List> map) {
                mView.showChannel(map.get(Const.Channel.DATA_SELECTED),
                        map.get(Const.Channel.DATA_UNSELECTED));
            }
        });
    }

    @Override
    public void getUserChannelFromLocal() {
        mLocalData.getUserChannel(mGetUserChannelListCallback);
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
