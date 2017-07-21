package com.moma.app.news.base;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.moma.app.news.R;
import com.moma.app.news.base.presenter.BasePresenter;
import com.moma.app.news.base.view.BaseView;
import com.moma.app.news.util.annotation.ActivityFragmentInject;
import com.moma.app.news.util.RxBus;

import rx.Observable;

/**
 * Created by moma on 17-7-17.
 */

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

        } else {
            throw new RuntimeException("Class must add annotations of ActivityFragmentInitParams.class");
        }

        try {
            setContentView(mContentViewId);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(mToolbarBackImageButtonId == -1){
            Toolbar myToolbar = initToolbar(mToolbarId,mToolbarTextViewId,mToolbarTextViewTitle);
        }else {
            Toolbar myToolbar = initDetailActivityToolbar(mToolbarId,mToolbarBackImageButtonId);
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
        AppCompatActivity activity = this;
        activity.setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }


    public Toolbar initDetailActivityToolbar(int toolBarId,int toolbarBackImageButtonId ) {
        Toolbar toolbar = (Toolbar) findViewById(toolBarId);
        ImageButton imageButton = (ImageButton) findViewById(toolbarBackImageButtonId);
        imageButton.setOnClickListener(this);
        AppCompatActivity activity = this;
        activity.setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }








    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
