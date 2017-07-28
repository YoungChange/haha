package com.hailer.news.contract.presenter;

import com.hailer.news.base.presenter.BasePresenterImpl;
import com.hailer.news.contract.model.IContractUsInteractor;
import com.hailer.news.contract.model.IContractUsInteractorImpl;
import com.hailer.news.contract.view.IContractUsView;
import com.hailer.news.util.bean.FeedBackMessage;

/**
 * Created by moma on 17-7-24.
 */

public class IContractUsPresenterImpl extends BasePresenterImpl<IContractUsView,FeedBackMessage> implements IContractUsPresenter{

    IContractUsInteractor<FeedBackMessage> mContractUsInteractor;


    public IContractUsPresenterImpl(IContractUsView view,FeedBackMessage message) {
        super(view);
        mContractUsInteractor = new IContractUsInteractorImpl();
        mSubscription = mContractUsInteractor.submitMeaaage(this, message.getUserEmail(), message.getMessageContent());
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}
