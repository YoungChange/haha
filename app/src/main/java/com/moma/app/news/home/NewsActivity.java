package com.moma.app.news.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.moma.app.news.base.BaseFragment;
import com.moma.app.news.R;
import com.moma.app.news.util.bean.NewsChannelBean;
import com.moma.app.news.util.other.CategoryDataUtils;
import com.moma.app.news.util.other.ListDataSave;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener{

    private TabLayout mTabLayout;
    private ViewPager mNewsViewpager;
    private ImageButton mChange_channel;

    private List<BaseFragment> fragments;
    // 个人channel
    private List<NewsChannelBean> myChannelList;
    // 更多channel
    private List<NewsChannelBean> moreChannelList;

    // 当前新闻频道的位置
    private int tabPosition;

    // 创建适配器，进行绑定
    private NewsFragmentPagerAdapter adapter;

    private SharedPreferences sharedPreferences;

    private boolean isFirst;

    // 用于保存Tags()
    // "myChannel" --> myChannelList
    // "moreChannel" --> moreChannelList
    private ListDataSave listDataSave;

    private BaseFragment baseFragment;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news);


        initView();

        initListener();

    }




    /**
     * 设置toolbar标题居中，没有返回键
     * @param id    toolbar的id
     * @param titleId   textView的id
     * @param titleString   textView设置的文字
     * @return  返回toolbar
     */
    public Toolbar initToolbar(int id, int titleId, int titleString) {
        Toolbar toolbar = (Toolbar) findViewById(id);
//        toolbar.setTitle("");
        TextView textView = (TextView) findViewById(titleId);
        textView.setText(titleString);
        AppCompatActivity activity = this;
        activity.setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }


    /**
     * 初始化View
     */
    public void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mNewsViewpager = (ViewPager) findViewById(R.id.news_viewpager);
        mChange_channel = (ImageButton) findViewById(R.id.change_channel);
        Toolbar myToolbar = initToolbar(R.id.my_toolbar, R.id.toolbar_title, R.string.news);

        initValidata();
        initTabLayout();
    }


    /**
     * adapter、viewpager、fragment 绑定
     */
    public void initValidata() {
//        sharedPreferences = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        sharedPreferences = this.getSharedPreferences("Setting", Context.MODE_PRIVATE);
        listDataSave = new ListDataSave(this, "channel");
        fragments = new ArrayList<BaseFragment>();
        adapter = new NewsFragmentPagerAdapter(getSupportFragmentManager(), this);

        mTabLayout.setupWithViewPager(mNewsViewpager);

        bindData();
    }

    /**
     * 初始化TabLayout
     */
    public void initTabLayout(){
//        mTabLayout.addTab(mTabLayout.newTab().setText("Tab1"));


        if (adapter.getCount()<= 4) {
            //当Tab较少时，使用MODE_FIXED模式（平分空间）
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            //当Tab较多时采用MODE_SCROLLABLE模式（可滑动）
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }



    public void bindData() {
        getDataFromSharedPreference();
        adapter.setChannelBean(myChannelList);
        adapter.setFragments(fragments);
        mNewsViewpager.setAdapter(adapter);
    }


    /**
     * 判断是否第一次进入程序
     * 如果第一次进入，直接获取设置好的频道
     * 如果不是第一次进入，则从sharedPrefered中获取设置好的频道
     */
    private void getDataFromSharedPreference() {
        isFirst = sharedPreferences.getBoolean("isFirst", true);
        if (isFirst) {
            myChannelList = CategoryDataUtils.getChannelCategoryBeans();
            moreChannelList = getMoreChannelFromAsset();
            myChannelList = setType(myChannelList);
            moreChannelList = setType(moreChannelList);
            listDataSave.setDataList("myChannel", myChannelList);
            listDataSave.setDataList("moreChannel", moreChannelList);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("isFirst", false);
            edit.commit();
        } else {
            myChannelList = listDataSave.getDataList("myChannel", NewsChannelBean.class);
        }
        fragments.clear();
        for (int i = 0; i < myChannelList.size(); i++) {
//            baseFragment = NewsListFragment.newInstance(myChannelList.get(i).getTid());
            baseFragment = SimpleFragment.newInstance(i+1,myChannelList.get(i).gettName());
            fragments.add(baseFragment);
        }


        if (myChannelList.size() <= 4) {
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

    }


    private List<NewsChannelBean> setType(List<NewsChannelBean> list) {
        Iterator<NewsChannelBean> iterator = list.iterator();
        while (iterator.hasNext()) {
            NewsChannelBean channelBean = iterator.next();
            channelBean.setTabType(1);
        }
        return list;
    }

    /**
     * 从Asset目录中读取更多频道
     *
     * @return
     */
    public List<NewsChannelBean> getMoreChannelFromAsset() {
        List<NewsChannelBean>  beans=new ArrayList<>();
        beans.add(new NewsChannelBean("头s条0"));
        beans.add(new NewsChannelBean("头s条1"));
        beans.add(new NewsChannelBean("头s条2"));
        beans.add(new NewsChannelBean("头s条3"));
        beans.add(new NewsChannelBean("头s条4"));
        beans.add(new NewsChannelBean("头s条5"));
        beans.add(new NewsChannelBean("头s条6"));
        return beans;
    }






    public void initListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mChange_channel.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change_channel:
                Intent intent = new Intent(this,ChannelManagerActivity.class);
                intent.putExtra("TABPOSITION", tabPosition);
                startActivityForResult(intent, 999);
            default:
                Toast.makeText(this, "没有响应事件", Toast.LENGTH_SHORT).show();

        }

    }
}
