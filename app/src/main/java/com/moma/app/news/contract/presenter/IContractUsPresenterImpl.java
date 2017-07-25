package com.moma.app.news.contract.presenter;

import com.moma.app.news.base.presenter.BasePresenterImpl;
import com.moma.app.news.contract.model.IContractUsInteractor;
import com.moma.app.news.contract.model.IContractUsInteractorImpl;
import com.moma.app.news.contract.view.IContractUsView;
import com.moma.app.news.util.bean.FeedBackMessage;

/**
 * Created by moma on 17-7-24.
 */

public class IContractUsPresenterImpl extends BasePresenterImpl<IContractUsView,FeedBackMessage> implements IContractUsPresenter{

    IContractUsInteractor<FeedBackMessage> mContractUsInteractor;


    public IContractUsPresenterImpl(IContractUsView view,FeedBackMessage message) {
        super(view);
        mContractUsInteractor = new IContractUsInteractorImpl();
        mSubscription = mContractUsInteractor.submitMeaaage(this,message);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}
