package com.hailer.news.channel;

import com.hailer.news.api.bean.CommentInfo;

import java.util.List;

/**
 * Created by moma on 17-8-24.
 */

public class ChannelContract {

    interface View {

    }

    interface Presenter {
        public void getUserChannelFromRemote();
        public void getUserChannelFromLocal();
        public void getAllChannelFromRemote();
        public void getAllChannelFromLocal();
    }
}
