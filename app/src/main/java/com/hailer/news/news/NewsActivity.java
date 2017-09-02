package com.hailer.news.news;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
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
import com.hailer.news.common.Const;
import com.hailer.news.common.ToolBarType;
import com.hailer.news.common.BaseActivity;
import com.hailer.news.login.LoginActivity;
import com.hailer.news.util.FuncUtil;
import com.hailer.news.util.GlideUtils;
import com.hailer.news.util.RxBus;
import com.hailer.news.util.VersionUtil;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.hailer.news.util.bean.ChannelInfo;
import com.hailer.news.util.bean.UserInfo;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import io.fabric.sdk.android.Fabric;

@ActivityFragmentInject(contentViewId = R.layout.activity_news,
        handleRefreshLayout = true,
        toolbarId = R.id.my_toolbar,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.app_name,
        hasNavigationView = true,
        toolbarType = ToolBarType.HasMenuButton)
public class NewsActivity extends BaseActivity implements NewsContract.View{
    private TabLayout mTabLayout;
    private ViewPager mNewsViewpager;
    private ImageButton mChange_channel;

    private NewsContract.Presenter mNewsPresenter;
    CircleImageView loginImageButton;

    private Context mContext;

    Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mNewsViewpager = (ViewPager) findViewById(R.id.news_viewpager);
        mChange_channel = (ImageButton) findViewById(R.id.change_channel);
        loginImageButton = (CircleImageView) findViewById(R.id.login_imagebutton);
        mNewsPresenter = new NewsPresenter(this);
        mNewsPresenter.autoLogin();
        mNewsPresenter.getUserChannel();
        trackingApp();
        mNewsPresenter.checkUpdate();
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
                Intent intent = new Intent(NewsActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.change_channel:
                mNewsPresenter.startChannelForSelected();
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
    public void showChannels(List<ChannelInfo> newsChannels){
        List<NewsListFragment> fragmentList = new ArrayList<>();
        final List<String> title = new ArrayList<>();
        if (newsChannels != null && newsChannels.size() > 0) {

            PagerAdapter adapter = mNewsViewpager.getAdapter();
            // 如何管理和重用Activity,NewsFragmentAdapter最清楚，讲数据传给它，在其内部处理重用。
            if (adapter == null) {
                adapter = new NewsFragmentAdapter(getSupportFragmentManager(), newsChannels);
                mNewsViewpager.setAdapter(adapter);
            } else {
                ((NewsFragmentAdapter) adapter).updateFragments(newsChannels);
            }
            mNewsViewpager.setCurrentItem(0, false);
            mTabLayout.setupWithViewPager(mNewsViewpager);
            mTabLayout.setScrollPosition(0, 0, true);
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

            FuncUtil.dynamicSetTabLayoutMode(mTabLayout);

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
    public void showNewsList(int loadType, List<NewsItem> list, int tabId){
        if (tabId == mNewsViewpager.getCurrentItem()) {
            NewsListFragment fragment = (NewsListFragment)getFragmentAt(tabId);
            if (fragment != null) {
                fragment.showNewsList(loadType, list);
            }
        }
    }

    @Override
    public void showErrorMsg(int mTabId, int loadType){
        NewsListFragment fragment = (NewsListFragment)getFragmentAt(mTabId);
        fragment.showLoadError();
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


    private Fragment getFragmentAt(int index){
        NewsFragmentAdapter adapter = (NewsFragmentAdapter) mNewsViewpager.getAdapter();
        Fragment fragment = adapter.getItem(index);

        if (!fragment.isAdded()) {
            KLog.e("has not added fragment!!!!!!!!!!!!!!!!!!!!");
            return null;
        }
        return fragment;
    }

    private Fragment getCurrentFragment(){
        int curItem = mNewsViewpager.getCurrentItem();
        NewsFragmentAdapter adapter = (NewsFragmentAdapter) mNewsViewpager.getAdapter();
        Fragment fragment = adapter.getItem(curItem);

        if (!fragment.isAdded()) {
            return null;
        }
        return fragment;
    }

    private void displayCurFragment(){
        NewsListFragment fragment = (NewsListFragment)getCurrentFragment();
        if (fragment != null) {
            fragment.display();
        }
    }

    protected void setOnTabSelectEvent(final ViewPager viewPager, final TabLayout tabLayout) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
                // 如果一个页面正在更新数据，就滑动到另一个页面，需要将其他页面的页面都置为可刷新，因为那些正在刷新数据
                // 的页面被标志为正在刷新。打开舒心功能才能在下次打开时从新加载数据。
                NewsFragmentAdapter adapter = (NewsFragmentAdapter) mNewsViewpager.getAdapter();
                for (int fragmentIndex = 0; fragmentIndex < adapter.getCount(); fragmentIndex++ ) {
                    NewsListFragment fragment = (NewsListFragment)adapter.getItem(fragmentIndex);
                    fragment.enableLoad();
                }
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

    public void loadMoreData(String mCatId, int totalItemCount) {
        mNewsPresenter.loadMoreData(mCatId, totalItemCount, mNewsViewpager.getCurrentItem());
    }

    public void getNewsList(String mCatId) {
        mNewsPresenter.getNewsList(mCatId,  mNewsViewpager.getCurrentItem());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<ChannelInfo> selectList = (ArrayList) data.getSerializableExtra(Const.Channel.SELECT_CHANNEL_LIST);
        showChannels(selectList);
    }


    public void showUpdateDialog(@NonNull String title, @NonNull String content) {
        new AlertDialog.Builder(this)

                .setTitle(title)
                .setMessage(content)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        VersionUtil.showMarket(mContext);
                    }
                })
                .create()
                .show();
    }
}
