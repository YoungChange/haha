package com.moma.app.news.home.presenter;

import com.moma.app.news.api.bean.NewsDetail;
import com.moma.app.news.base.presenter.BasePresenterImpl;
import com.moma.app.news.home.model.INewsDetailInteractor;
import com.moma.app.news.home.model.INewsDetailInteractorImpl;
import com.moma.app.news.home.view.INewsDetailView;

/**
 * ClassName: INewsDetailPresenterImpl<p>
 * Author: oubowu<p>
 * Fuction: 新闻详情代理接口实现<p>
 * CreateDate: 2016/2/19 21:11<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public class INewsDetailPresenterImpl extends BasePresenterImpl<INewsDetailView, NewsDetail>
        implements INewsDetailPresenter {

    public INewsDetailPresenterImpl(INewsDetailView newsDetailView, String postId) {
        super(newsDetailView);
        INewsDetailInteractor<NewsDetail> newsDetailInteractor = new INewsDetailInteractorImpl();
        mSubscription = newsDetailInteractor.requestNewsDetail(this, postId);
    }

    @Override
    public void requestSuccess(NewsDetail data) {
        mView.initNewsDetail(data);
    }

    @Override
    public void requestError(String msg) {
        super.requestError(msg);
        mView.toast(msg);
    }
}
