package com.moma.app.news.home.presenter;

//import com.oushangfeng.ounews.base.BasePresenterImpl;
//import com.oushangfeng.ounews.greendao.NewsChannelTable;
//import com.oushangfeng.ounews.module.news.model.INewsInteractor;
//import com.oushangfeng.ounews.module.news.model.INewsInteractorImpl;
//import com.oushangfeng.ounews.module.news.view.INewsView;

import com.moma.app.news.base.presenter.BasePresenterImpl;
import com.moma.app.news.home.model.INewsInteractor;
import com.moma.app.news.home.model.INewsInteractorImpl;
import com.moma.app.news.home.view.INewsView;
import com.moma.app.news.util.bean.NewsChannelBean;

import java.util.List;

/**
 * Fuction: 新闻代理接口实现<p>
 */
public class INewsPresenterImpl extends BasePresenterImpl<INewsView, List<NewsChannelBean>>
        implements INewsPresenter {

    private INewsInteractor<List<NewsChannelBean>> mNewsInteractor;

    public INewsPresenterImpl(INewsView newsView) {
        super(newsView);
        mNewsInteractor = new INewsInteractorImpl();
        mSubscription = mNewsInteractor.operateChannel(this);
//        mView.initRxBusEvent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void requestSuccess(List<NewsChannelBean> data) {
        mView.initViewPager(data);
    }

    @Override
    public void operateChannel() {
        mSubscription = mNewsInteractor.operateChannel(this);
    }
}
