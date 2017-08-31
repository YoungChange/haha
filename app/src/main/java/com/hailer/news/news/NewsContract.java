package com.hailer.news.news;

import android.support.annotation.NonNull;

import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.util.bean.ChannelInfo;

import java.util.List;

/**
 * Created by moma on 17-8-1.
 */
public interface NewsContract {
    interface View {
        void showChannels(List<ChannelInfo> data);

        void showNewsList(int loadType, List<NewsItem> list, int tabId);

        void upateUserView();

        void showErrorMsg(int tabId, int loadType);

        void showUpdateDialog(@NonNull String title, @NonNull String context);
    }

    interface Presenter {

        void refreshData(String catId,int itemCount);

        void loadMoreData(String catId, int itemCount);

        void autoLogin();

        void getUserChannel();

        void getNewsList(String catId, int tabId);

        void startChannelForSelected();

        void checkUpdate();
    }
}
