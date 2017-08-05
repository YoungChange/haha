package com.hailer.news.home.presenter;

import com.hailer.news.UserManager;
import com.hailer.news.api.bean.LoginInfo;
import com.hailer.news.base.presenter.BasePresenterImpl;
import com.hailer.news.home.model.ILoginInteractor;
import com.hailer.news.home.model.ILoginInteractorImpl;
import com.hailer.news.home.view.ILoginView;
import com.hailer.news.util.bean.UserInfo;

/**
 * Created by moma on 17-7-31.
 */

public class ILoginPresenterImpl extends BasePresenterImpl<ILoginView, LoginInfo> implements ILoginPresenter {

    private UserInfo userinfo;
    private ILoginInteractor<LoginInfo> mLoginInteractor;

    public ILoginPresenterImpl(ILoginView view, UserInfo userinfo) {
        super(view);
        this.userinfo = userinfo;
        mLoginInteractor = new ILoginInteractorImpl();
        mSubscription = mLoginInteractor.login(this, userinfo);
    }

    @Override
    public void requestSuccess(LoginInfo loginInfo) {
        UserManager.getInstance().saveUserInfo(loginInfo);
        mView.loginSuccess();
    }

    @Override
    public void requestError(String msg ) {
        super.requestError(msg);
    }

}
