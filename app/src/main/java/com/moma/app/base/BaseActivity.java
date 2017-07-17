package com.moma.app.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.moma.app.base.presenter.BasePresenter;
import com.moma.app.util.annotation.ActivityInject;

/**
 * Created by moma on 17-7-17.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity{


    /**
     * 将代理类通用行为抽出来
     */
    protected T mPresenter;

    /**
     * 布局的id
     */
    protected int mContentViewId;

    /**
     * Toolbar标题
     */
    private int mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getClass().isAnnotationPresent(ActivityInject.class)){
            ActivityInject anno = getClass().getAnnotation(ActivityInject.class);
            mContentViewId = anno.contentViewId();
            mToolbarTitle  = anno.toolbarTitle();
        } else {
            throw new RuntimeException("Class must add annotations of ActivityFragmentInitParams.class");
        }

        setContentView(mContentViewId);


        initView();

    }




    protected abstract void initView();

}
