package com.hailer.news.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.LoginButton;
import com.hailer.news.R;
import com.hailer.news.UserManager;
import com.hailer.news.common.ToolBarType;
import com.hailer.news.common.BaseActivity;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.socks.library.KLog;

/**
 * Created by moma on 17-7-31.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_login_by_facebook,
                toolbarType = ToolBarType.NoToolbar
)
public class LoginActivity extends BaseActivity implements LoginContract.View{

    CallbackManager mFBCallbackMgr;
    LoginButton mLoginBtn;
    ImageButton deleteImageButton;
    ProfileTracker profileTracker;

    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;

//    LoginPresenter mLoginPresenter;
    LoginContract.Presenter mLoginPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoginPresenter = new LoginPresenter(this);
//        LoginManager.getInstance().logOut();

        deleteImageButton = (ImageButton) findViewById(R.id.delete_button);
        deleteImageButton.setOnClickListener(this);

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                KLog.e("----onCurrentAccessTokenChanged----");
                if(currentAccessToken==null){
                    KLog.d("------------currentAccessToken==null");
                    UserManager.getInstance().setUserInfo(null,null,null,null);
                    UserManager.getInstance().setServerToken(null);
                }

            }
        };

        mFBCallbackMgr = CallbackManager.Factory.create();
        mLoginBtn = (LoginButton) findViewById(R.id.login_button);
        mLoginPresenter.login(mFBCallbackMgr, mLoginBtn);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFBCallbackMgr.onActivityResult(requestCode, resultCode, data);

        KLog.e("----onCurrentAccessTokenChanged----");
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.delete_button:
                //this.finish();
                goBack();
                break;
            default:
                toast(this.getString(R.string.unknow_error));
        }
    }

    @Override
    public void showLoginSuccess() {
        goBack();
    }

    @Override
    public void showLoginFailed(int error) {
        //
    }

    private void goBack(){
        //关闭登录界面
        this.finish();
    }

}
