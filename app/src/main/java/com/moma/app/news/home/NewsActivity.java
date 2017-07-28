package com.moma.app.news.home;


import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;


import com.moma.app.news.base.BaseActivity;
import com.moma.app.news.base.BaseFragment;
import com.moma.app.news.R;
import com.moma.app.news.base.BaseFragmentAdapter;
import com.moma.app.news.home.presenter.INewsPresenter;
import com.moma.app.news.home.presenter.INewsPresenterImpl;
import com.moma.app.news.home.view.INewsView;
import com.moma.app.news.util.RxBus;
import com.moma.app.news.util.annotation.ActivityFragmentInject;
import com.moma.app.news.util.bean.NewsChannelBean;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

@ActivityFragmentInject(contentViewId = R.layout.activity_news,
        handleRefreshLayout = true,
        toolbarId = R.id.my_toolbar,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.moma,
        hasNavigationView = true)
public class NewsActivity extends BaseActivity<INewsPresenter> implements INewsView {

    private TabLayout mTabLayout;
    private ViewPager mNewsViewpager;
    private ImageButton mChange_channel;

    private BaseFragment baseFragment;

    private Observable<Boolean> mChannelObservable;

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

}
