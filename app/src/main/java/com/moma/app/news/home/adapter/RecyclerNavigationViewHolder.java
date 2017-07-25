package com.moma.app.news.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moma.app.news.R;

/**
 * Created by moma on 17-7-24.
 */

public class RecyclerNavigationViewHolder extends BaseRecyclerViewHolder{

    ImageView itemImageView;
    TextView itemTextView;

    public RecyclerNavigationViewHolder(View itemView) {
        super(itemView);
        itemImageView = itemView.findViewById(R.id.nav_item_image);
        itemTextView = itemView.findViewById(R.id.nav_item_title);
    }

    public RecyclerNavigationViewHolder(Context context, View itemView) {
        this(itemView);
        mContext = context;
    }
}
