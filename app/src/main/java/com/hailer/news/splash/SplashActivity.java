package com.hailer.news.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;


import com.hailer.news.R;
import com.hailer.news.common.BaseActivity;
import com.hailer.news.common.ToolBarType;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.socks.library.KLog;

/**
 * Created by moma on 17-8-16.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_news,
        handleRefreshLayout = true,
        toolbarId = R.id.my_toolbar,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.app_name,
        hasNavigationView = true,
        toolbarType = ToolBarType.HasMenuButton)
public class SplashActivity extends BaseActivity implements SplashContract.View{
    private Handler mTimerHander; // 定时
    SplashContract.Presenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPresenter = new SplashPresenter(this, new SplashModel());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTimerHander == null) {
            mTimerHander = new Handler();
            mTimerHander.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPresenter.startNewsActivity();
                    finish();
                }
            }, 1000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTimerHander != null) {
            mTimerHander.removeCallbacksAndMessages(null);
        }
        mTimerHander = null;
    }

    @Override
    public void showAdvertising() {
        KLog.e("SplashActivity -------show AD");
    }

    @Override
    public void onClick(View view) {

    }
}
