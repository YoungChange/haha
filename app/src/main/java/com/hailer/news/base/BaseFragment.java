package com.hailer.news.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hailer.news.base.presenter.BasePresenter;
import com.hailer.news.base.view.BaseView;
import com.hailer.news.util.annotation.ActivityFragmentInject;


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

            try{
                mFragmentRootView = inflater.inflate(mContentViewId, container, false);
            }catch (Exception e){
                throw e;
            }

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
//        mRefreshLayout = (RelativeLayout) mFragmentRootView.findViewById(R.id.refresh_layout);
//        if (mRefreshLayout != null) {
//
//            final RecyclerView recyclerView = (RecyclerView) mFragmentRootView
//                    .findViewById(R.id.recycler_view);
//
//            mAppbarOffsetObservable = RxBus.get()
//                    .register("enableRefreshLayoutOrScrollRecyclerView", Object.class);
//            mAppbarOffsetObservable.subscribe(new Action1<Object>() {
//                @Override
//                public void call(Object obj) {
//                    if (obj instanceof Integer) {
//                        if (!mIsStop && recyclerView != null && (Integer) obj == mPosition) {
//                            // 当前Fragment所在的Activity可见并且是选中的Fragment才处理事件
//                            recyclerView.smoothScrollToPosition(0);
//                        }
//                    } else if (obj instanceof Boolean) {
//                        mRefreshLayout.setRefreshable((Boolean) obj);
//                    }
//                }
//            });
//        }
    }


    public BaseFragment() {
    }

    protected abstract void initView(View fragmentRootView);


    @Override
    public void toast(String msg) {
        ((BaseActivity)getActivity()).toast(msg);
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
