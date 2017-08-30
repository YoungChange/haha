package com.hailer.news.channel;

import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.util.bean.ChannelInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by moma on 17-8-24.
 */

public class ChannelContract implements Serializable {

    interface View {
        void showChannel(List<ChannelInfo> userChannelList, List<ChannelInfo> OtherChannelList);
    }
    interface Presenter {
        void getChannels();
        void getUserChannelFromLocal();
        void updateChannel(List<ChannelInfo> list);
        //以后会用
        void onItemAdd(String channelName);
        void onItemRemove(String channelName);
        void onItemSwap(int fromPos, int toPos);
    }
}
