package com.hailer.news.common;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.socks.library.KLog;

/**
 * Created by moma on 17-9-2.
 * 解决Inconsistency detected. Invalid view holder adapter positionViewHolder异常
 * 只是把这个异常捕获了，不让它奔溃，问题的终极解决方案还是得让google去修复。
 *
 * 只后设置RecyclerView的布局管理为WrapContentLinearLayoutManager对象
 */

public class WrapContentLinearLayoutManager extends LinearLayoutManager {
    public WrapContentLinearLayoutManager(Context context) {
        super(context);
    }

    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            KLog.e("meet a IOOBE in RecyclerView");
        }
    }
}
