package com.hailer.news.home;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hailer.news.R;
import com.hailer.news.base.BaseActivity;
import com.hailer.news.contract.presenter.IContractUsPresenterImpl;
import com.hailer.news.home.presenter.ILoginPresenter;
import com.hailer.news.home.presenter.ILoginPresenterImpl;
import com.hailer.news.home.view.ILoginView;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.hailer.news.util.bean.UserInfo;

/**
 * Created by moma on 17-7-31.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_login,
        handleRefreshLayout = true,
        toolbarId = R.id.back_toolbar,
        toolbarBackImageButtonId = R.id.back_imagebutton,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.login_or_register
)
public class LoginActivity extends BaseActivity<ILoginPresenter> implements ILoginView{

    private UserInfo mUserInfo;
    private EditText username_edittext;
    private EditText password_edittext;
    private Button login_button;

    @Override
    protected void initView() {
        username_edittext = (EditText) findViewById(R.id.username_edittext);
        password_edittext = (EditText) findViewById(R.id.password_edittext);
        login_button = (Button) findViewById(R.id.login_button);
        mUserInfo = new UserInfo(username_edittext.getText().toString(),password_edittext.getText().toString());
        login_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_imagebutton:
                this.finish();
                break;
            case R.id.login_button:
                mPresenter = new ILoginPresenterImpl(this,mUserInfo);
                break;
            default:
                toast(this.getString(R.string.what_you_did));
        }
    }
}
