package com.hailer.news.channel;

import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.util.bean.ChannelInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by moma on 17-8-24.
 */

public class ChannelContract implements Serializable {

    interface View {
        void showChannel(ArrayList<String> channelList);
    }
    interface Presenter {
        public void getUserChannelFromLocal();
        public void getOtherChannelFromLocal();
        public void updateChannel(List<ChannelInfo> list);


        //以后会用
        public void onItemAdd(String channelName);
        public void onItemRemove(String channelName);
        public void onItemSwap(int fromPos, int toPos);
    }
}
