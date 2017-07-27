package com.moma.app.news.home.presenter;



import com.moma.app.news.api.APIConfig;
import com.moma.app.news.api.bean.NewsItem;
import com.moma.app.news.base.DataLoadType;
import com.moma.app.news.base.presenter.BasePresenterImpl;
import com.moma.app.news.home.model.INewsListInteractor;
import com.moma.app.news.home.model.INewsListInteractorImpl;
import com.moma.app.news.home.view.INewsListView;
import com.socks.library.KLog;

import java.util.List;

/**
 * ClassName: INewsListPresenterImpl<p>
 * Fuction: 新闻列表代理接口实现<p>
 * CreateDate: 2016/2/18 14:39<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
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
        //mView.updateNewsList(null, e, mIsRefresh ? DataLoadType.TYPE_REFRESH_FAIL : DataLoadType.TYPE_LOAD_MORE_FAIL);
    }

    @Override
    public void requestSuccess(List<NewsItem> data) {
        KLog.e("request.......bailei.....mStartpage="+mStartPage);
        if (data != null) {
            mStartPage += APIConfig.LIST_ITEMS_PER_PAGE;
        }

        mView.updateNewsList(data, "", mIsRefresh ? DataLoadType.TYPE_REFRESH_SUCCESS : DataLoadType.TYPE_LOAD_MORE_SUCCESS);

    }

    @Override
    public void refreshData() {
        //bailei modify 0 to 1
        KLog.e("refreshData().......bailei.....");
        mStartPage = 1;
        mIsRefresh = true;
        mSubscription = mNewsListInteractor.requestNewsList(this, mNewsType, mNewsId, mStartPage);
    }

    @Override
    public void loadMoreData() {
        KLog.e("request.......bailei.....mNewsType="+mNewsType+", mNewsId="+mNewsId);
        mIsRefresh = false;
        mSubscription = mNewsListInteractor.requestNewsList(this, mNewsType, mNewsId, mStartPage);
    }



}
