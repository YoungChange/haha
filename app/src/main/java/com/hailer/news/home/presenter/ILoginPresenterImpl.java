package com.hailer.news.home.presenter;

import com.hailer.news.UserManager;
import com.hailer.news.base.presenter.BasePresenterImpl;
import com.hailer.news.home.model.ILoginInteractor;
import com.hailer.news.home.model.ILoginInteractorImpl;
import com.hailer.news.home.view.ILoginView;
import com.hailer.news.util.bean.UserInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by moma on 17-7-31.
 */

public class ILoginPresenterImpl extends BasePresenterImpl<ILoginView, String> implements ILoginPresenter {

//    IContractUsInteractor<UserInfo> mContractUsInteractor;

    private UserInfo userinfo;
    private ILoginInteractor<String> mLoginInteractor;

    public ILoginPresenterImpl(ILoginView view, UserInfo userinfo) {
        super(view);
        this.userinfo = userinfo;
        mLoginInteractor = new ILoginInteractorImpl();
        mSubscription = mLoginInteractor.login(this, userinfo);
    }

    @Override
    public void requestSuccess(String token) {
       UserManager.getInstance().setServerToken(token);
    }

}
