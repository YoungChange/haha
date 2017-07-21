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

    //集合类，layout里包含的View,以view的id作为key，value是view对象
    protected SparseArray<View> mViews;
    //上下文对象
    protected Context mContext;

    ImageView news_summary_photo;
    TextView news_summary_title;
    TextView news_summary_digest;
    TextView news_summary_ptime;



    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        news_summary_photo = itemView.findViewById(R.id.iv_news_summary_photo);
        news_summary_title = itemView.findViewById(R.id.tv_news_summary_title);
        news_summary_digest = itemView.findViewById(R.id.tv_news_summary_digest);
        news_summary_ptime = itemView.findViewById(R.id.tv_news_summary_ptime);
    }

    public BaseRecyclerViewHolder(Context context, View itemView) {
        this(itemView);
        mContext = context;

//        mViews = new SparseArray<View>();
    }






}
