package com.hailer.news.model;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hailer.news.UserManager;
import com.hailer.news.common.ErrMsg;
import com.hailer.news.common.RxCallback;
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

    public void login(CallbackManager callbackManager, LoginButton loginButton){
        loginButton.setReadPermissions("public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code  如果登录成功，LoginResult 参数将拥有新的 AccessToken 及最新授予或拒绝的权限。
                KLog.e("-------------facebook login onSuccess()");
                //UserManager.getInstance().requestFBToken();
                mCallBack.requestSuccess(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                mCallBack.requestError(ErrMsg.UNKNOW_ERROR);
            }

            @Override
            public void onError(FacebookException exception) {
                KLog.e("----FacebookCallback-----onError---exception:"+exception.toString());
                mCallBack.requestError(ErrMsg.UNKNOW_ERROR);
            }
        });

    }

}
