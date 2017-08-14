package com.hailer.news.news;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hailer.news.R;
import com.hailer.news.UserManager;
import com.hailer.news.base.ErrMsg;
import com.hailer.news.base.presenter.BaseDataSource;
import com.hailer.news.home.LoginByFacebookActivity;
import com.hailer.news.home.presenter.ILoginPresenterImpl;
import com.hailer.news.util.bean.UserInfo;
import com.socks.library.KLog;
import android.support.annotation.NonNull;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Fuction:
 */
public class FacebookDataSource {
    private RxCallback mCallBack;

    public FacebookDataSource(@NonNull RxCallback callback){
        mCallBack = checkNotNull(callback, "callback cannot be null");
    }

    public static String getToken(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            return null;
        }
        UserManager.getInstance().setUserInfo(null, null, null, accessToken.getToken());

        return accessToken.getToken();
    }

    void login(CallbackManager callbackManager, LoginButton loginButton){
        loginButton.setReadPermissions("public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code  如果登录成功，LoginResult 参数将拥有新的 AccessToken 及最新授予或拒绝的权限。
                KLog.e("-------------facebook login onSuccess()");
                //UserManager.getInstance().requestFBToken();
                mCallBack.requestSuccess(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                mCallBack.requestError(ErrMsg.UNKNOW_ERROR);
            }

            @Override
            public void onError(FacebookException exception) {
                mCallBack.requestError(ErrMsg.UNKNOW_ERROR);
            }
        });

    }

}