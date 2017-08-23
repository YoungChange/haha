package com.hailer.news.common;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hailer.news.R;
import com.hailer.news.news.NewsListFragment;

/**
 * Created by moma on 17-8-22.
 */

public class LoadTips {
    protected View mLoadingTip;
    protected View mLoadErrorTip;
    public LoadTips(Context context, int mLayoutRes) {
       // mLoadingTip =
    }

    public LoadTips(Fragment fragment) {


    }

    public void showLoading(boolean noData) {
        if (noData) {
            //showTip()
        }
    }

    public void showLoadError() {

    }
//
//    public static View showTips(View targetView, TipsType tipsType) {
//        View tips = TipsType.createTips(targetView.getContext());
//        return tips.applyTo(targetView, tipsType.ordinal());
//    }
    enum TipsType {
        LOADING(R.layout.tip_loading),
        FAILED(R.layout.tips_no_data);

        protected int mLayoutRes;

        TipsType(int layoutRes) {
            this.mLayoutRes = layoutRes;
        }

         LoadTips createTips(Context context) {
            return new LoadTips(context, mLayoutRes);
        }

    }

    public class Tip {

    }
}
