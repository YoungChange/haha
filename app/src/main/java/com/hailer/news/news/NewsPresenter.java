package com.hailer.news.news;

import com.hailer.news.UserManager;
import com.hailer.news.api.APIConfig;
import com.hailer.news.base.presenter.BaseModel;
import com.hailer.news.base.presenter.BasePresenter;
import com.hailer.news.base.presenter.DataCallback;
import com.hailer.news.base.presenter.IBasePresenter;
import com.hailer.news.base.presenter.IBasePresenterImpl;
import com.hailer.news.base.view.BaseView;
import com.socks.library.KLog;

/**
 * Created by moma on 17-7-17.
 */

public class NewsPresenter implements NewsContract.Presenter {
    private NewsContract.View mView;
    private RemoteDataSource mRemoteData;
    private int mStartPage = 0;
    private DataCallback mDataCallback;
    private RxCallback mGetDataCallback;
    private RxCallback mLoginCallback;
    private RxCallback mPostDataCallback;

    public NewsPresenter(NewsContract.View view) {
        mView = view;
        mRemoteData = new RemoteDataSource();

        mGetDataCallback = new RxCallback() {
            @Override
            public void requestError(String msg) {
                mView.showErrorMsg();
            }

            @Override
            public void requestSuccess(Object data) {
                mStartPage += APIConfig.LIST_ITEMS_PER_PAGE;
                mView.showNewsList();
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
    }

    @Override
    public void refreshData() {
        KLog.i("refreshData()............");
        mStartPage = 1;
        //refresh
        mRemoteData.getNewsList("", mStartPage, mGetDataCallback);
    }

    @Override
    public void loadMoreData() {
        //load data
        mRemoteData.getNewsList("", mStartPage, mGetDataCallback);
    }

}
