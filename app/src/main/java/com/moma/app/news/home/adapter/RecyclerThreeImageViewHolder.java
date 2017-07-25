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

public class RecyclerThreeImageViewHolder  extends BaseRecyclerViewHolder{

    ImageView news_summary_photo1;
    ImageView news_summary_photo2;
    ImageView news_summary_photo3;
    TextView news_summary_title;
    TextView news_summary_time;


    public RecyclerThreeImageViewHolder(View itemView) {
        super(itemView);
        news_summary_photo1 = itemView.findViewById(R.id.news_summary_photo1);
        news_summary_photo2 = itemView.findViewById(R.id.news_summary_photo2);
        news_summary_photo3 = itemView.findViewById(R.id.news_summary_photo3);
        news_summary_title = itemView.findViewById(R.id.news_summary_title);
        news_summary_time = itemView.findViewById(R.id.news_summary_time);
    }

    public RecyclerThreeImageViewHolder(Context context, View itemView) {
        this(itemView);
        mContext = context;
    }
}
