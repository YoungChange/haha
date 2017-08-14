package com.hailer.news.common;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.hailer.news.R;
import com.hailer.news.common.BaseRecyclerViewHolder;

public class RecyclerNavigationViewHolder extends BaseRecyclerViewHolder {

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
