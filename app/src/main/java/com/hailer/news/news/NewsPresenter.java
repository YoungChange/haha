package com.hailer.news.news;


import com.hailer.news.UserManager;
import com.hailer.news.api.bean.LoginInfo;
import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.channel.ChannelActivity;
import com.hailer.news.common.LoadType;
import com.hailer.news.common.RxCallback;
import com.hailer.news.model.FacebookDataSource;
import com.hailer.news.model.LocalDataSource;
import com.hailer.news.model.RemoteDataSource;
import com.hailer.news.util.bean.ChannelInfo;

import java.util.List;

/**
 * Created by moma on 17-7-17.
 *
 *
 */
public class NewsPresenter implements NewsContract.Presenter {
    private NewsContract.View mView;
    private RemoteDataSource mRemoteData;
    private LocalDataSource mLocalData;
    private int mStartPage = 0;
    private RxCallback mLocalCB;
    private NewsLoadedCallBacek mGetDataCallback;
    private RxCallback mLoginCallback;
    private int mLoadType = LoadType.TYPE_REFRESH;

    public NewsPresenter(NewsContract.View view) {
        mView = view;

        mLocalCB = new RxCallback<List<ChannelInfo>>() {
            @Override
            public void requestError(int msg) {
                //mView.showErrorMsg(msg, mLoadType);
                // 频道加载应该保证至少几个是可以使用的，如果没能加载远端数据则仅显示
                // 本地的几条。连频道都提示出错的话也太令人茫然了。
            }

            @Override
            public void requestSuccess(List< ChannelInfo > data) {
                mView.showChannels(data);
            }
        };

        mGetDataCallback = new NewsLoadedCallBacek();

        mLoginCallback = new RxCallback<LoginInfo>() {
            @Override
            public void requestError(int msg) {
                //mView.showErrorMsg();
            }

            @Override
            public void requestSuccess(LoginInfo loginInfo) {
                //ToDo save serverToken
                UserManager.getInstance().saveUserInfo(loginInfo);
                mView.upateUserView();
            }
        };

        mRemoteData = new RemoteDataSource();
        mLocalData = new LocalDataSource();

    }

    @Override
    public void autoLogin() {
        if (FacebookDataSource.getToken() != null) {
            mRemoteData.login(UserManager.getInstance().getUserinfo(), mLoginCallback);
        } else {
            //是否提示facebook未登录
        }
    }

    @Override
    public void getUserChannel() {
        mLocalData.getUserChannel(mLocalCB);
    }

    @Override
    public void getNewsList(String catId, int tabId) {
        //load data
        mLoadType = LoadType.TYPE_REFRESH;
        mRemoteData.getNewsList(catId, mStartPage, mGetDataCallback.setTabId(tabId));
    }

    @Override
    public void startChannelForSelected() {
        ChannelActivity.startChannelForResult((NewsActivity)mView);
    }

    @Override
    public void refreshData(String catId, int itemCount) {
//        mStartPage = 1;
        mLoadType = LoadType.TYPE_REFRESH;
        //refresh
        mRemoteData.getNewsList(catId, 0, mGetDataCallback);
    }

    @Override
    public void loadMoreData(String catId, int itemCount) {
        //load data
        mLoadType = LoadType.TYPE_LOAD_MORE;
        mRemoteData.getNewsList(catId, itemCount, mGetDataCallback);
    }

    class NewsLoadedCallBacek implements RxCallback<List<NewsItem>> {
        int mTabId = 0;

        @Override
        public void requestError(int msg) {
            mView.showErrorMsg(mTabId, mLoadType);
        }

        @Override
        public void requestSuccess(List<NewsItem> data) {
            // mStartPage += APIConfig.LIST_ITEMS_PER_PAGE;
            mView.showNewsList(mLoadType, data, mTabId);
        }

        public NewsLoadedCallBacek setTabId(int tabId) {
            mTabId = tabId;
            return this;
        }

        public String getTabId() {
            return getTabId();
        }
    }
}
