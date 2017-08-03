package com.hailer.news.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hailer.news.R;
import com.hailer.news.base.presenter.BasePresenter;
import com.hailer.news.base.view.BaseView;
import com.hailer.news.contract.ContractUsActivity;
import com.hailer.news.home.LoginByFacebookActivity;
import com.hailer.news.home.adapter.OnItemClickListener;
import com.hailer.news.home.adapter.RecyclerNavigationAdapter;
import com.hailer.news.util.MeasureUtil;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.hailer.news.util.RxBus;
import com.hailer.news.util.bean.NavigationItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;


public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements View.OnClickListener, BaseView {


    /**
     * 将代理类通用行为抽出来
     */
    protected T mPresenter;

    /**
     * 布局的id
     */
    protected int mContentViewId;

    /**
     * Toolbar  id
     */
    private int mToolbarId;

    /**
     * mToolbarTextViewId
     */
    private int mToolbarTextViewId;

    /**
     * mToolbarTextView 內容  “新闻”
     */
    private int mToolbarTextViewTitle;

    private int mToolbarBackImageButtonId;

    private boolean mHasNavigationView;

    private int mToolBarType;

    //滑动布局
    protected DrawerLayout mDrawerLayout;

    private RecyclerNavigationAdapter mNavAdapter;

    /**
     * 结束Activity的可观测对象
     */
    private Observable<Boolean> mFinishObservable;

    /**
     * 跳转的类
     */
    private Class mClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getClass().isAnnotationPresent(ActivityFragmentInject.class)){
            ActivityFragmentInject anno = getClass().getAnnotation(ActivityFragmentInject.class);
            mContentViewId = anno.contentViewId();
            mToolbarId  = anno.toolbarId();
            mToolbarTextViewId = anno.toolbarTextViewId();
            mToolbarTextViewTitle = anno.toolbarTextViewTitle();
            mToolbarBackImageButtonId = anno.toolbarBackImageButtonId();
            mHasNavigationView = anno.hasNavigationView();
            mToolBarType = anno.toolbarType();
        } else {
            throw new RuntimeException("Class must add annotations of ActivityFragmentInitParams.class");
        }

        setContentView(mContentViewId);


        if (mHasNavigationView) {
            initNavigationView();
        }

        if(mToolBarType == ToolBarType.HasMenuButton){
            Toolbar myToolbar = initToolbar(mToolbarId,mToolbarTextViewId,mToolbarTextViewTitle);
        }else if(mToolBarType == ToolBarType.HasBackButton){
            Toolbar myToolbar = initBackActivityToolbar(mToolbarId,mToolbarBackImageButtonId,mToolbarTextViewId,mToolbarTextViewTitle);
        }else{

        }

        initView();

    }

    protected abstract void initView();

    /**
     * 设置toolbar标题居中，没有返回键
     * @param id    toolbar的id
     * @param titleId   textView的id
     * @param titleString   textView设置的文字
     * @return  返回toolbar
     */
    public Toolbar initToolbar(int id, int titleId, int titleString) {

        Toolbar toolbar = (Toolbar) findViewById(id);

        TextView textView = (TextView) findViewById(titleId);
        textView.setText(titleString);

        ImageButton menuImageButton = (ImageButton) findViewById(R.id.menu_imagebutton);
        menuImageButton.setOnClickListener(this);

        ImageButton loginImageButton = (ImageButton) findViewById(R.id.login_imagebutton);
        loginImageButton.setOnClickListener(this);

        AppCompatActivity activity = this;
        activity.setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }


    public Toolbar initBackActivityToolbar(int toolBarId,int toolbarBackImageButtonId, int titleId, int titleString) {

        Toolbar toolbar = (Toolbar) findViewById(toolBarId);

        ImageButton imageButton = (ImageButton) findViewById(toolbarBackImageButtonId);
        imageButton.setOnClickListener(this);

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




    private void initNavigationView() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        LinearLayout navigation_view = (LinearLayout) findViewById(R.id.navigation_view);
        RecyclerView nav_recycler_view = (RecyclerView) findViewById(R.id.nav_recycler_view);
        // 设置DrawerLayout的响应
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.setClickable(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        List<NavigationItem> data = new ArrayList<NavigationItem>();
        data.add(new NavigationItem(R.string.contract_us,R.drawable.contact));

        if(mNavAdapter == null){
            mNavAdapter = new RecyclerNavigationAdapter(this,data);
        }
        mNavAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                mDrawerLayout.closeDrawer(GravityCompat.START);
                Intent intent ;
                switch (position){
                    case 0:
                        intent = new Intent(BaseActivity.this, ContractUsActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(BaseActivity.this, R.string.what_you_did, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        //添加分割线
        nav_recycler_view.addItemDecoration(
                new BaseRecycleViewDivider(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        MeasureUtil.dip2px(this,1),
                        getResources().getColor(R.color.divide_newslist)));

        nav_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        nav_recycler_view.setAdapter(mNavAdapter);

    }



    private Toast toast;
    @Override
    public void toast(String msg) {
        if(toast==null){
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            toast.show();
        }else{
            toast.cancel();
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    protected void setOnTabSelectEvent(final ViewPager viewPager, final TabLayout tabLayout) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
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
