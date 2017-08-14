package com.hailer.news.news;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hailer.news.R;
import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.common.BaseRecyclerViewHolder;
import com.hailer.news.util.FuncUtil;
import com.hailer.news.util.GlideUtils;

/**
 * NewsList的ViewHolder类
 */

public class NewsListViewHolder extends BaseRecyclerViewHolder {

    ImageView news_summary_photo1 = null;
    ImageView news_summary_photo2 = null;
    ImageView news_summary_photo3 = null;
    TextView news_summary_title;
    TextView news_summary_time;
    int mViewType;

    public NewsListViewHolder(Context context, View itemView, int viewType) {
        super(itemView);

        mContext = context;
        mViewType = viewType;

        news_summary_title = itemView.findViewById(R.id.news_summary_title);
        news_summary_time = itemView.findViewById(R.id.news_summary_time);

        if (viewType == NewsItemViewType.ONEIMAGE) {
            news_summary_photo1 = itemView.findViewById(R.id.news_summary_photo);
        } else if (viewType == NewsItemViewType.THREEIMAGE) {
            news_summary_photo1 = itemView.findViewById(R.id.news_summary_photo1);
            news_summary_photo2 = itemView.findViewById(R.id.news_summary_photo2);
            news_summary_photo3 = itemView.findViewById(R.id.news_summary_photo3);
        }

    }

    public void setTitle(String title) {
        news_summary_title.setText(title);
    }

    public void setDate(String date) {
        news_summary_time.setText(FuncUtil.time2Time(date));
    }

    public void setImage(NewsItem item) {
        if (item == null) {
            return;
        }
        //图片加载，如果是gif图片只加载第一帧, 设置asGif为false
        if (mViewType == NewsItemViewType.ONEIMAGE) {
            GlideUtils.loadDefault(item.getImageList().get(0), news_summary_photo1, false, null, DiskCacheStrategy.RESULT);
        } else if (mViewType == NewsItemViewType.THREEIMAGE) {
            GlideUtils.loadDefault(item.getImageList().get(0), news_summary_photo1, false, null, DiskCacheStrategy.RESULT);
            GlideUtils.loadDefault(item.getImageList().get(1), news_summary_photo2, false, null, DiskCacheStrategy.RESULT);
            GlideUtils.loadDefault(item.getImageList().get(2), news_summary_photo3, false, null, DiskCacheStrategy.RESULT);
        }
    }
}
