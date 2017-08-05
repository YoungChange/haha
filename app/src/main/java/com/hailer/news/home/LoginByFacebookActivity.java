package com.hailer.news.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hailer.news.R;
import com.hailer.news.UserManager;
import com.hailer.news.base.BaseActivity;
import com.hailer.news.base.ToolBarType;
import com.hailer.news.home.presenter.ILoginPresenter;
import com.hailer.news.home.presenter.ILoginPresenterImpl;
import com.hailer.news.home.view.ILoginView;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.hailer.news.util.bean.UserInfo;
import com.socks.library.KLog;

/**
 * Created by moma on 17-7-31.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_login_by_facebook,
                toolbarType = ToolBarType.NoToolbar
)
public class LoginByFacebookActivity extends BaseActivity<ILoginPresenter> implements ILoginView{

    CallbackManager callbackManager;
    LoginButton loginButton;
    ImageButton deleteImageButton;
    ProfileTracker profileTracker;

    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //LoginManager.getInstance().logOut();

        deleteImageButton = (ImageButton) findViewById(R.id.delete_button);
        deleteImageButton.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code  如果登录成功，LoginResult 参数将拥有新的 AccessToken 及最新授予或拒绝的权限。
                KLog.e("-------------facebook login onSuccess()");
                UserManager.getInstance().requestFBToken();

                UserInfo userInfo = UserManager.getInstance().getUserinfo();
                if (userInfo.getPlatformToken() != null) {
                    mPresenter = new ILoginPresenterImpl(LoginByFacebookActivity.this, userInfo);
                }
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                if(currentAccessToken==null){
                    KLog.d("------------currentAccessToken==null");
                    UserManager.getInstance().setUserInfo(null,null,null,null);
                    UserManager.getInstance().setServerToken(null);
                    logoutSuccess();
                }

            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.delete_button:
                this.finish();
                break;
            default:
                toast(this.getString(R.string.unknow_error));
        }
    }

    @Override
    protected void initView() {
        //null
    }

    @Override
    public void loginSuccess() {
        boolean isLogin=true;
        Intent intent = new Intent();
        intent.putExtra("isLogin",isLogin);
        setResult(2,intent);
        deleteImageButton.callOnClick();
    }

    public void logoutSuccess() {
        boolean isLogin=false;
        Intent intent = new Intent();
        intent.putExtra("isLogin",isLogin);
        setResult(2,intent);
    }
}
