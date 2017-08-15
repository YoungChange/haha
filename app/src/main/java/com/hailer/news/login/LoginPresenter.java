package com.hailer.news.login;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.hailer.news.UserManager;
import com.hailer.news.api.bean.LoginInfo;
import com.hailer.news.common.ErrMsg;
import com.hailer.news.model.FacebookDataSource;
import com.hailer.news.model.RemoteDataSource;
import com.hailer.news.common.RxCallback;

/**
 * Created by moma on 17-7-17.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;
    private RemoteDataSource mRemoteData;
    private FacebookDataSource mFacebookData;
    private int mStartPage = 0;
    private RxCallback mRemoteLoginCB;
    private RxCallback mFacebookLoginCB;
    private RxCallback mPostDataCallback;

    public LoginPresenter(LoginContract.View view) {
        mView = view;

        mFacebookLoginCB = new RxCallback<String>() {
            @Override
            public void requestError(int msg) {
                mView.showLoginFailed(ErrMsg.UNKNOW_ERROR);
            }

            @Override
            public void requestSuccess(String token) {
                UserManager.getInstance().setPlatformToken(token);
                mRemoteData.login(UserManager.getInstance().getUserinfo(), mRemoteLoginCB);
            }
        };


        mRemoteLoginCB = new RxCallback<LoginInfo>() {
            @Override
            public void requestError(int msg) {
                mView.showLoginFailed(ErrMsg.LOAD_DATA_ERROR);
            }

            @Override
            public void requestSuccess(LoginInfo loginInfo) {
                mView.showLoginSuccess();
            }
        };

        mRemoteData = new RemoteDataSource();
        mFacebookData = new FacebookDataSource(mFacebookLoginCB);

    }

    @Override
    public void login(CallbackManager callbackManager, LoginButton button) {
        mFacebookData.login(callbackManager,button);
    }

}
