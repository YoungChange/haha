package com.moma.app.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.moma.app.base.BaseActivity;
import com.moma.app.news.presenter.INewsPresenterImpl;
import com.moma.app.util.annotation.ActivityInject;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener{

    private TabLayout mTabLayout;
    private ViewPager mNewsViewpager;
    private ImageButton mChange_channel;

    // 当前新闻频道的位置
    private int tabPosition;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news);


        initView();

        initListener();

    }




    /**
     * 设置toolbar标题居中，没有返回键
     * @param view
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


    public void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mNewsViewpager = (ViewPager) findViewById(R.id.news_viewpager);
        mChange_channel = (ImageButton) findViewById(R.id.change_channel);

        Toolbar myToolbar = initToolbar(R.id.my_toolbar, R.id.toolbar_title, R.string.news);

        initTabLayout();


    }

    public void initTabLayout(){
//        mTabLayout.addTab(mTabLayout.newTab().setText("Tab1"));

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this);
        mNewsViewpager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mNewsViewpager);


        if (adapter.getCount()<= 4) {
            //当Tab较少时，使用MODE_FIXED模式（平分空间）
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            //当Tab较多时采用MODE_SCROLLABLE模式（可滑动）
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
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
