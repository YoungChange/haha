package com.hailer.news.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hailer.news.R;
import com.hailer.news.UserManager;
import com.hailer.news.base.BaseActivity;
import com.hailer.news.base.ToolBarType;
import com.hailer.news.home.presenter.ILoginPresenter;
import com.hailer.news.home.presenter.ILoginPresenterImpl;
import com.hailer.news.home.view.ILoginView;
import com.hailer.news.news.LoginContract;
import com.hailer.news.news.LoginPresenter;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.hailer.news.util.bean.UserInfo;
import com.socks.library.KLog;

/**
 * Created by moma on 17-7-31.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_login_by_facebook,
                toolbarType = ToolBarType.NoToolbar
)
public class LoginActivity extends BaseActivity<ILoginPresenter> implements LoginContract.View{

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

        mFBCallbackMgr = CallbackManager.Factory.create();
        mLoginBtn = (LoginButton) findViewById(R.id.login_button);
        mLoginPresenter.login(mFBCallbackMgr, mLoginBtn);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFBCallbackMgr.onActivityResult(requestCode, resultCode, data);

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
    protected void initView() {
        //null
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
