package com.hailer.news.news;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.hailer.news.BuildConfig;
import com.hailer.news.NewsApplication;
import com.hailer.news.R;
import com.hailer.news.UserManager;
import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.base.BaseFragment;
import com.hailer.news.base.ToolBarType;
import com.hailer.news.home.presenter.ILoginPresenterImpl;
import com.hailer.news.home.view.NewsFragmentAdapter;
import com.hailer.news.util.GlideUtils;
import com.hailer.news.util.RxBus;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.hailer.news.util.bean.NewsChannelBean;
import com.hailer.news.util.bean.UserInfo;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.fabric.sdk.android.Fabric;
import rx.Observable;

@ActivityFragmentInject(contentViewId = R.layout.activity_news,
        handleRefreshLayout = true,
        toolbarId = R.id.my_toolbar,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.app_name,
        hasNavigationView = true,
        toolbarType = ToolBarType.HasMenuButton)
public class NewsListActivity extends HailerActivity implements NewsContract.View{

    private TabLayout mTabLayout;
    private ViewPager mNewsViewpager;
    private ImageButton mChange_channel;

    private BaseFragment baseFragment;

    private Observable<Boolean> mChannelObservable;

    private ILoginPresenterImpl mLoginPresenter;

    private NewsContract.Presenter mNewsPresenter;

    CircleImageView loginImageButton;

    Tracker mTracker;
    private String CATEGORY_ID_RECENT = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginImageButton = (CircleImageView) findViewById(R.id.login_imagebutton);
        mNewsPresenter = new NewsPresenter(this);
        mNewsPresenter.autoLogin();
        mNewsPresenter.getUserChannel();

        trackingApp();

    }


    @Override
    protected void onResume() {
        super.onResume();
        upateUserView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        RxBus.get().unregister("channelChange", mChannelObservable);
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
//                if(UserManager.getInstance().getServerToken()==null || UserManager.getInstance().getServerToken().isEmpty()){
                    Intent intent = new Intent(NewsListActivity.this, LoginActivity.class);
                    startActivity(intent);
//                }
                break;
        }
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
                    toast(getString(R.string.exit_msg));
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
    public void showChannels(List<NewsChannelBean> newsChannels){
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mNewsViewpager = (ViewPager) findViewById(R.id.news_viewpager);
        mChange_channel = (ImageButton) findViewById(R.id.change_channel);

        KLog.e("bailei ----show channels");

        List<NewsListFragment> fragments = new ArrayList<>();
        final List<String> title = new ArrayList<>();
        int i = 0;
        if (newsChannels != null) {
            for (NewsChannelBean news : newsChannels) {
                final NewsListFragment fragment = NewsListFragment
                        .newInstance(news.getTabName(),news.getTabId());

                fragment.setPresenter(mNewsPresenter);
                fragments.add(fragment);
                title.add(news.getTabName());
            }

            if (mNewsViewpager.getAdapter() == null) {
                NewsFragmentAdapter adapter = new NewsFragmentAdapter(getSupportFragmentManager(),
                        fragments, title);
                mNewsViewpager.setAdapter(adapter);
            } else {
                final NewsFragmentAdapter adapter = (NewsFragmentAdapter) mNewsViewpager.getAdapter();
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

        displayCurFragment();
    }

    @Override
    public void upateUserView(){
        UserInfo userInfo = UserManager.getInstance().getUserinfo();
        if(userInfo.getIconUri()==null || userInfo.getIconUri()==""){
            loginImageButton.setImageResource(R.drawable.login);
        }else{
            GlideUtils.loadDefault(userInfo.getIconUri(), loginImageButton, false, null, DiskCacheStrategy.RESULT);
        }
    }

    @Override
    public void showNewsList(int loadType, List<NewsItem> list){
        NewsListFragment fragment = (NewsListFragment)getCurrentFragment();
        if (fragment != null) {
            fragment.showNewsList(loadType, list);
        }
    }

    @Override
    public void showErrorMsg(){

    }

    private void trackingApp(){
        if (BuildConfig.DEBUG) {
            return;
        }
        NewsApplication application = (NewsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("News list Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Fabric.with(this, new Crashlytics());
    }

    private Fragment getCurrentFragment(){
        int curItem = mNewsViewpager.getCurrentItem();
        NewsFragmentAdapter adapter = (NewsFragmentAdapter) mNewsViewpager.getAdapter();
        Fragment fragment = adapter.getItem(curItem);

        if (!fragment.isAdded()) {
            KLog.e("has not added fragment!!!!!!!!!!!!!!!!!!!!");
            return null;
        }

        return fragment;
    }

    private void updateCurFragment(){
        NewsListFragment fragment = (NewsListFragment)getCurrentFragment();
        if (fragment != null) {
            fragment.refreshList();
        }
    }

    private void displayCurFragment(){
        NewsListFragment fragment = (NewsListFragment)getCurrentFragment();
        if (fragment != null) {
            fragment.display();
        }
    }

    private void setOnTabSelectEvent(final ViewPager viewPager, final TabLayout tabLayout) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
                displayCurFragment();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                RxBus.get().post("enableRefreshLayoutOrScrollRecyclerView", tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });
    }


}
