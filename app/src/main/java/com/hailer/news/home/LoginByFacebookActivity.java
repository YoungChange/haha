package com.hailer.news.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hailer.news.R;
import com.hailer.news.base.BaseActivity;
import com.hailer.news.home.presenter.ILoginPresenter;
import com.hailer.news.home.presenter.ILoginPresenterImpl;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.socks.library.KLog;

/**
 * Created by moma on 17-7-31.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_login_by_facebook,
        handleRefreshLayout = true,
        toolbarId = R.id.back_toolbar,
        toolbarBackImageButtonId = R.id.back_imagebutton,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.login_or_register
)
public class LoginByFacebookActivity extends BaseActivity<ILoginPresenter> {

    CallbackManager callbackManager;
    LoginButton loginButton;
    ProfileTracker profileTracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code  如果登录成功，LoginResult 参数将拥有新的 AccessToken 及最新授予或拒绝的权限。
                toast("onSuccess");
                KLog.d("getRecentlyDeniedPermissions："+loginResult.getRecentlyDeniedPermissions());
                KLog.d("getRecentlyGrantedPermissions："+loginResult.getRecentlyGrantedPermissions());
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

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                if(oldProfile!=null){
                    KLog.d("oldProfile.getFirstName():"+oldProfile.getFirstName());
                    KLog.d("oldProfile.getId():"+oldProfile.getId());
                    KLog.d("oldProfile.getLastName():"+oldProfile.getLastName());
                    KLog.d("oldProfile.getProfilePictureUri():"+oldProfile.getProfilePictureUri(100,100));
                    KLog.d("oldProfile.getLinkUri():"+oldProfile.getLinkUri());

                }else{
                    KLog.d("oldProfile is null");
                }
                if(currentProfile!=null){
                    KLog.d("currentProfile.getFirstName():"+currentProfile.getFirstName());
                    KLog.d("currentProfile.getId():"+currentProfile.getId());
                    KLog.d("currentProfile.getLastName():"+currentProfile.getLastName());
                    KLog.d("currentProfile.getProfilePictureUri():"+currentProfile.getProfilePictureUri(100,100));
                    KLog.d("currentProfile.getLinkUri():"+currentProfile.getLinkUri());
                }else{
                    KLog.d("currentProfile is null");
                }


            }
        };

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_imagebutton:
                this.finish();
                break;
            default:
                toast(this.getString(R.string.what_you_did));
        }
    }
}
