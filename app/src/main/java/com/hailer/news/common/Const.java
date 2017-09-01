package com.hailer.news.common;

/**
 * Created by moma on 17-8-29.
 *
 */

public interface Const {

    interface App{
        String PACKAGE_NAME = "com.hailer.news";
    }

    interface Channel {
        String DATA_SELECTED = "DataSelected";
        String DATA_UNSELECTED = "DataUnselected";
        String SELECT_CHANNEL_LIST = "SelectChannelList";
    }

    interface Activity {
        int START_CHANNEL_FOR_RESULE = 200;
        int RESPONSE_CODE_FROM_CHANNEL = 201;
    }


}
