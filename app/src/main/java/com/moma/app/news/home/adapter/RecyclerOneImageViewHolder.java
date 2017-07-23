package com.moma.app.news.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moma.app.news.R;

/**
 * Created by moma on 17-7-22.
 */

public class RecyclerOneImageViewHolder  extends BaseRecyclerViewHolder{

    //上下文对象
    protected Context mContext;

    ImageView news_summary_photo;
    TextView news_summary_title;
    TextView news_summary_time;



    public RecyclerOneImageViewHolder(View itemView) {
        super(itemView);
        news_summary_photo = itemView.findViewById(R.id.news_summary_photo);
        news_summary_title = itemView.findViewById(R.id.news_summary_title);
        news_summary_time = itemView.findViewById(R.id.news_summary_time);
    }

    public RecyclerOneImageViewHolder(Context context, View itemView) {
        this(itemView);
        mContext = context;
    }
}
