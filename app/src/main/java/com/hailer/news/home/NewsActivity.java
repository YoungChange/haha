package com.hailer.news.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.hailer.news.NewsApplication;
import com.hailer.news.UserManager;
import com.hailer.news.base.BaseActivity;
import com.hailer.news.base.BaseFragment;
import com.hailer.news.R;
import com.hailer.news.base.BaseFragmentAdapter;
import com.hailer.news.base.ToolBarType;
import com.hailer.news.home.presenter.ILoginPresenterImpl;
import com.hailer.news.home.presenter.INewsPresenter;
import com.hailer.news.home.presenter.INewsPresenterImpl;
import com.hailer.news.home.view.ILoginView;
import com.hailer.news.home.view.INewsView;
import com.hailer.news.util.GlideUtils;
import com.hailer.news.util.RxBus;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.hailer.news.util.bean.NewsChannelBean;
import com.hailer.news.util.bean.UserInfo;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.functions.Action1;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

@ActivityFragmentInject(contentViewId = R.layout.activity_news,
        handleRefreshLayout = true,
        toolbarId = R.id.my_toolbar,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.moma,
        hasNavigationView = true,
        toolbarType = ToolBarType.HasMenuButton)
public class NewsActivity extends BaseActivity<INewsPresenter> implements INewsView, ILoginView{

    private TabLayout mTabLayout;
    private ViewPager mNewsViewpager;
    private ImageButton mChange_channel;

    private BaseFragment baseFragment;

    private Observable<Boolean> mChannelObservable;

    private ILoginPresenterImpl mLoginPresenter;

    CircleImageView loginImageButton;

    //登录的状态
    boolean mLoginStatus = false;

    Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginImageButton = (CircleImageView) findViewById(R.id.login_imagebutton);

        UserManager.getInstance().requestFBToken();

        UserInfo userInfo = UserManager.getInstance().getUserinfo();
        Boolean loggedIn = userInfo.getPlatformToken() != null;
        KLog.e("facebook 登录状态："+loggedIn);
        if (loggedIn) {
            mLoginPresenter = new ILoginPresenterImpl(this, userInfo);
        }

        NewsApplication application = (NewsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("News list Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fabric.with(this, new Crashlytics());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("channelChange", mChannelObservable);
    }

    @Override
    protected void initView() {
        mPresenter = new INewsPresenterImpl(this);
    }

    public void initListener() {
        mChange_channel.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_imagebutton:
                this.mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.login_imagebutton:
                if(UserManager.getInstance().getServerToken()==null || UserManager.getInstance().getServerToken().isEmpty()){
                    Intent intent = new Intent(NewsActivity.this, LoginByFacebookActivity.class);
                    startActivityForResult(intent,0);
                }
                break;
        }
    }
    @Override
    public void initViewPager(List<NewsChannelBean> newsChannels) {

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mNewsViewpager = (ViewPager) findViewById(R.id.news_viewpager);
        mChange_channel = (ImageButton) findViewById(R.id.change_channel);

        List<BaseFragment> fragments = new ArrayList<>();
        final List<String> title = new ArrayList<>();
        int i = 0;
        if (newsChannels != null) {
            // 有除了固定的其他频道被选中，添加

            for (NewsChannelBean news : newsChannels) {
                final SimpleFragment fragment = SimpleFragment
                        .newInstance(news.getTabType(), news.getTabName(),news.getTabId());

                fragments.add(fragment);
                title.add(news.getTabName());
            }

            if (mNewsViewpager.getAdapter() == null) {
                BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(),
                        fragments, title);
                mNewsViewpager.setAdapter(adapter);
            } else {
                final BaseFragmentAdapter adapter = (BaseFragmentAdapter) mNewsViewpager.getAdapter();
                adapter.updateFragments(fragments, title);
            }
            mNewsViewpager.setCurrentItem(0, false);
            mTabLayout.setupWithViewPager(mNewsViewpager);
            mTabLayout.setScrollPosition(0, 0, true);
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);

            setOnTabSelectEvent(mNewsViewpager, mTabLayout);

        } else {
            toast(this.getString(R.string.data_error));
        }

        initListener();
    }
    @Override
    public void initRxBusEvent() {
        mChannelObservable = RxBus.get().register("channelChange", Boolean.class);
        mChannelObservable.subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean change) {
                if (change) {
                    mPresenter.operateChannel();
                }
            }
        });
    }

    private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    toast(getString(R.string.press_again_to_exit));
                    firstTime = secondTime;
                    return true;
                } else {
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void loginSuccess() {
        //换头像
        UserInfo userInfo = UserManager.getInstance().getUserinfo();
        if(userInfo.getIconUri()!=null){
            mLoginStatus = true;
            GlideUtils.loadDefault(userInfo.getIconUri(), loginImageButton, false, null, DiskCacheStrategy.RESULT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == 2){
            if (data == null) {

            }else{
                if(data.getExtras().getBoolean("isLogin")){
                    loginSuccess();
                }else{
                    logoutSuccess();
                }
            }
        }
    }

    public void logoutSuccess() {
        UserInfo userInfo = UserManager.getInstance().getUserinfo();
        if(userInfo.getIconUri()==null){
            mLoginStatus = false;
//            GlideUtils.loadDefault(userInfo.getIconUri(), loginImageButton, false, null, DiskCacheStrategy.RESULT);
            loginImageButton.setImageResource(R.drawable.login);
        }
    }
}
