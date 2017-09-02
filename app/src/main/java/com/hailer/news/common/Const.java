package com.hailer.news.common;

/**
 * Created by moma on 17-8-29.
 *
 */

public interface Const {
    interface App{
        String PACKAGE_NAME = "com.hailer.news";
    }

    // 关于用户标识的Preference
    interface Identification {
        String PREFERENCE = "UserIdentification";
        String UUID = "uuid";

    }
    // 频道分类标识
    interface Channel {
        String DATA_SELECTED = "DataSelected";
        String DATA_UNSELECTED = "DataUnselected";
        String SELECT_CHANNEL_LIST = "SelectChannelList";
    }

    // Activity之间通信的请求和返回码
    interface Activity {
        int START_CHANNEL_FOR_RESULE = 200;
        int RESPONSE_CODE_FROM_CHANNEL = 201;
    }


}
