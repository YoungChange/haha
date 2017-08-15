package com.hailer.news.login;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

/**
 * Created by moma on 17-8-1.
 */
public interface LoginContract {
    interface View {

        void showLoginSuccess();

        void showLoginFailed(int error);
    }

    interface Presenter {

        void login(CallbackManager callbackManager, LoginButton button);

    }
}
