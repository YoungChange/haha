package com.hailer.news.news;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.api.bean.LoginInfo;
import com.hailer.news.base.presenter.IBasePresenter;
import com.hailer.news.base.view.BaseView;

import java.util.List;

/**
 * Created by moma on 17-8-1.
 */
public interface LoginContract {
    interface View extends BaseView {

        void showLoginSuccess();

        void showLoginFailed(int error);
    }

    interface Presenter extends IBasePresenter {

        void login(CallbackManager callbackManager, LoginButton button);

    }
}
