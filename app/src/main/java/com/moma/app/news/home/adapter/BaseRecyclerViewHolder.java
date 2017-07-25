package com.moma.app.news.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moma.app.news.R;

/**
 * Created by moma on 17-7-20.
 */

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    //上下文对象
    protected Context mContext;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
    }
}
