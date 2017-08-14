package com.hailer.news.login;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.hailer.news.contract.IBasePresenter;

/**
 * Created by moma on 17-8-1.
 */
public interface LoginContract {
    interface View {

        void showLoginSuccess();

        void showLoginFailed(int error);
    }

    interface Presenter extends IBasePresenter {

        void login(CallbackManager callbackManager, LoginButton button);

    }
}
