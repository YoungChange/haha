package com.hailer.news.news;

import com.hailer.news.UserManager;
import com.hailer.news.api.APIConfig;
import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.base.presenter.BaseModel;
import com.hailer.news.base.presenter.BasePresenter;
import com.hailer.news.base.presenter.DataCallback;
import com.hailer.news.base.presenter.IBasePresenter;
import com.hailer.news.base.presenter.IBasePresenterImpl;
import com.hailer.news.base.view.BaseView;
import com.hailer.news.util.bean.NewsChannelBean;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by moma on 17-7-17.
 */

public class NewsPresenter implements NewsContract.Presenter {
    private NewsContract.View mView;
    private RemoteDataSource mRemoteData;
    private LocalDataSource mLocalData;
    private int mStartPage = 0;
    private RxCallback mLocalCB;
    private RxCallback mGetDataCallback;
    private RxCallback mLoginCallback;
    private int mLoadType = LoadType.TYPE_REFRESH;

    public NewsPresenter(NewsContract.View view) {
        mView = view;

        mLocalCB = new RxCallback<List< NewsChannelBean >>() {
            @Override
            public void requestError(String msg) {
                mView.showErrorMsg();
            }

            @Override
            public void requestSuccess(List< NewsChannelBean > data) {
                mView.showChannels(data);
            }
        };

        mGetDataCallback = new RxCallback<List<NewsItem>>() {
            @Override
            public void requestError(String msg) {
                mView.showErrorMsg();
            }

            @Override
            public void requestSuccess(List<NewsItem> data) {
//                mStartPage += APIConfig.LIST_ITEMS_PER_PAGE;
                mView.showNewsList(mLoadType, data);
            }
        };

        mLoginCallback = new RxCallback() {
            @Override
            public void requestError(String msg) {
                //mView.showErrorMsg();
            }

            @Override
            public void requestSuccess(Object data) {
                //ToDo save serverToken

                mView.upateUserView();
            }
        };

        mRemoteData = new RemoteDataSource();
        mLocalData = new LocalDataSource(mLocalCB);

    }

    @Override
    public void start() {
        //load data
        //mDataSource.getNewsList(categoryId, mStartPage);
    }

    @Override
    public void getNewsList(String catId) {
        //load data
        mRemoteData.getNewsList(catId, mStartPage, mGetDataCallback);
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
        mLocalData.getChannel();
    }

    @Override
    public void refreshData(String catId) {
        KLog.i("bailei refreshData()............");
//        mStartPage = 1;
        mLoadType = LoadType.TYPE_REFRESH;
        //refresh
        mRemoteData.getNewsList(catId, 0, mGetDataCallback);
    }

    @Override
    public void loadMoreData(String catId, int itemCount) {
        //load data
        KLog.i("bailei loadMoreData()............");
        mLoadType = LoadType.TYPE_LOAD_MORE;
        mRemoteData.getNewsList(catId, itemCount, mGetDataCallback);
    }

}
