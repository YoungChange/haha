package com.moma.app.news.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.moma.app.news.R;
import com.moma.app.news.util.FuncUtil;
import com.moma.app.news.util.GlideUtils;

/**
 * Created by moma on 17-7-22.
 */

public class ListItemViewHolder  extends BaseRecyclerViewHolder{

    ImageView news_summary_photo1 = null;
    ImageView news_summary_photo2 = null;
    ImageView news_summary_photo3 = null;
    TextView news_summary_title;
    TextView news_summary_time;
    int mViewType;

    public ListItemViewHolder(Context context, View itemView, int viewType) {
        super(itemView);

        mContext = context;
        mViewType = viewType;

        news_summary_title = itemView.findViewById(R.id.news_summary_title);
        news_summary_time = itemView.findViewById(R.id.news_summary_time);

        if (viewType == ItemViewType.ONEIMAGE) {
            news_summary_photo1 = itemView.findViewById(R.id.news_summary_photo);
        } else if (viewType == ItemViewType.THREEIMAGE) {
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

    public void setImage(Object obj) {
        if (obj == null) {
            return;
        }
        //图片加载，如果是gif图片只加载第一帧, 设置asGif为false
        if (mViewType == ItemViewType.ONEIMAGE) {
            GlideUtils.loadDefault(obj, news_summary_photo1, false, null, DiskCacheStrategy.RESULT);
        } else if (mViewType == ItemViewType.THREEIMAGE) {
            GlideUtils.loadDefault(obj, news_summary_photo1, false, null, DiskCacheStrategy.RESULT);
            GlideUtils.loadDefault(obj, news_summary_photo2, false, null, DiskCacheStrategy.RESULT);
            GlideUtils.loadDefault(obj, news_summary_photo3, false, null, DiskCacheStrategy.RESULT);
        }
    }
}
