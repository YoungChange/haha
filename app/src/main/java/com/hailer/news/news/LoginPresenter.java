package com.hailer.news.news;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.hailer.news.UserManager;
import com.hailer.news.api.APIConfig;
import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.api.bean.LoginInfo;
import com.hailer.news.base.ErrMsg;
import com.hailer.news.base.presenter.DataCallback;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by moma on 17-7-17.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;
    private RemoteDataSource mRemoteData;
    private FacebookDataSource mFacebookData;
    private int mStartPage = 0;
    private DataCallback mDataCallback;
    private RxCallback mRemoteLoginCB;
    private RxCallback mFacebookLoginCB;
    private RxCallback mPostDataCallback;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        mRemoteData = new RemoteDataSource();

        mFacebookData = new FacebookDataSource(mFacebookLoginCB);

        mFacebookLoginCB = new RxCallback<String>() {
            @Override
            public void requestError(String msg) {
                mView.showLoginFailed(0);
            }

            @Override
            public void requestSuccess(String token) {
                UserManager.getInstance().setPlatformToken(token);
                mRemoteData.login(UserManager.getInstance().getUserinfo(), mRemoteLoginCB);
            }
        };


        mRemoteLoginCB = new RxCallback<LoginInfo>() {
            @Override
            public void requestError(String msg) {
                mView.showLoginFailed(0);
            }

            @Override
            public void requestSuccess(LoginInfo loginInfo) {
                mView.showLoginSuccess();
            }
        };

    }

    @Override
    public void start() {
        //load data
        //mDataSource.getNewsList(categoryId, mStartPage);
    }

    @Override
    public void login(CallbackManager callbackManager, LoginButton button) {
        mFacebookData.login(callbackManager,button);
    }

}
