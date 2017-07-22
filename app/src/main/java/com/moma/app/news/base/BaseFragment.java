package com.moma.app.news.base;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.moma.app.news.base.presenter.BasePresenter;
import com.moma.app.news.base.view.BaseView;
import com.moma.app.news.util.RxBus;
import com.moma.app.news.util.annotation.ActivityFragmentInject;


import rx.Observable;
import rx.functions.Action1;


public abstract class BaseFragment<T extends BasePresenter> extends Fragment
        implements BaseView, View.OnClickListener {


    // 将代理类通用行为抽出来
    protected T mPresenter;

    protected View mFragmentRootView;
    protected int mContentViewId;
    // 是否处理RefreshLayout与AppbarLayout的冲突
    private boolean mHandleRefreshLayout;

    private RelativeLayout mRelativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (null == mFragmentRootView) {
            if (getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
                ActivityFragmentInject annotation = getClass()
                        .getAnnotation(ActivityFragmentInject.class);
                mContentViewId = annotation.contentViewId();
                mHandleRefreshLayout = annotation.handleRefreshLayout();

            } else {
                throw new RuntimeException(
                        "Class must add annotations of ActivityFragmentInitParams.class");
            }

            mFragmentRootView = inflater.inflate(mContentViewId, container, false);

            if (mHandleRefreshLayout) {
                initRefreshLayoutOrRecyclerViewEvent();
            }

            initView(mFragmentRootView);
        }

        return mFragmentRootView;
    }





    /**
     * 订阅事件处理RefreshLayout
     */
    private void initRefreshLayoutOrRecyclerViewEvent() {

    }


    public BaseFragment() {
    }

    protected abstract void initView(View fragmentRootView);



    private Toast mToast;
    @Override
    public void toast(String msg) {

        if(mToast == null){
            mToast = Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT);
        }else{
            mToast.setText(msg);
        }
        mToast.show();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onClick(View v) {

    }
}
