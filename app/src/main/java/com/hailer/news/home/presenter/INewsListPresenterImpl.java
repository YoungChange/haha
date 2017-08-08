package com.hailer.news.home.presenter;

import com.hailer.news.api.APIConfig;
import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.base.DataLoadType;
import com.hailer.news.base.presenter.BasePresenterImpl;
import com.hailer.news.home.model.INewsListInteractor;
import com.hailer.news.home.model.INewsListInteractorImpl;
import com.hailer.news.home.view.INewsListView;
import com.socks.library.KLog;

import java.util.List;

/**
 * Fuction: 新闻列表代理接口实现
 */
public class INewsListPresenterImpl extends BasePresenterImpl<INewsListView, List<NewsItem>> implements INewsListPresenter {

    private INewsListInteractor<List<NewsItem>> mNewsListInteractor;
    private String mNewsType;
    private String mNewsId;
    private int mStartPage = 0;

    private boolean mIsRefresh = true;
    private boolean mHasInit;

    public INewsListPresenterImpl(INewsListView newsListView, String newsId, String newsType) {
        super(newsListView);
        mNewsListInteractor = new INewsListInteractorImpl();
        mSubscription = mNewsListInteractor.requestNewsList(this, newsType, newsId, mStartPage);
        KLog.d("bailei isUnsubscribed = "+mSubscription.isUnsubscribed()+", mSubscription="+mSubscription);
        mNewsType = newsType;
        mNewsId = newsId;
    }

    @Override
    public void beforeRequest() {
        if (!mHasInit) {
            mHasInit = true;
            mView.showProgress();
        }
    }

    @Override
    public void requestError(String e) {
        KLog.e("request.......error.....e="+e);
        super.requestError(e);
        mView.updateNewsList(null, e, mIsRefresh ? DataLoadType.TYPE_REFRESH_FAIL : DataLoadType.TYPE_LOAD_MORE_FAIL);
    }

    @Override
    public void requestComplete() {
        KLog.d("bailei requestComplete() isUnsubscribed = "+mSubscription.isUnsubscribed());
        super.requestComplete();
    }

    @Override
    public void requestSuccess(List<NewsItem> data) {
        KLog.i("request............mStartpage="+mStartPage);
        if (data != null) {
            mStartPage += APIConfig.LIST_ITEMS_PER_PAGE;
        }

        mView.updateNewsList(data, "", mIsRefresh ? DataLoadType.TYPE_REFRESH_SUCCESS : DataLoadType.TYPE_LOAD_MORE_SUCCESS);

    }

    @Override
    public void refreshData() {
        KLog.i("refreshData()............");
        mStartPage = 1;
        mIsRefresh = true;
        mSubscription = mNewsListInteractor.requestNewsList(this, mNewsType, mNewsId, mStartPage);
    }

    @Override
    public void loadMoreData() {
        KLog.i("request...........mNewsType="+mNewsType+", mNewsId="+mNewsId);
        mIsRefresh = false;
        KLog.d("bailei isUnsubscribed = "+mSubscription.isUnsubscribed()+", mSubscription="+mSubscription);
        mSubscription = mNewsListInteractor.requestNewsList(this, mNewsType, mNewsId, mStartPage);
    }



}
