package com.hailer.news.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hailer.news.R;
import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.common.BaseRecyclerAdapter;
import com.hailer.news.common.BaseRecyclerViewHolder;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * NewsList的adapter类
 */

public  class NewsListAdapter extends BaseRecyclerAdapter<NewsItem> {

    public NewsListAdapter(Context context, List<NewsItem> data) {
        super(context, data, null);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case  NewsItemViewType.THREEIMAGE:
                view =  mInflater.inflate(R.layout.item_news_three_image, parent, false);
                break;
            case NewsItemViewType.ONEIMAGE:
                view =  mInflater.inflate(R.layout.item_news_one_image, parent, false);
                break;
            case NewsItemViewType.NOIMAGE:
                view =  mInflater.inflate(R.layout.item_news_no_image, parent, false);
                break;
            case NewsItemViewType.FOOTER:
                view = mInflater.inflate(R.layout.item_news_footer, parent, false);
        }
        if (viewType == NewsItemViewType.FOOTER) {
            return new FooterViewHolder(view);
        } else {
            final NewsListViewHolder holder = new NewsListViewHolder(mContext, view, viewType);

            if (mClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if(holder.getLayoutPosition()!= RecyclerView.NO_POSITION){
                            try{
                                mClickListener.onItemClick(view,holder.getLayoutPosition());
                            }catch(Exception e){
                                KLog.d("set ItemView listener failed");
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        if ( holder instanceof NewsListViewHolder) {
            NewsItem item = mData.get(position);

            ((NewsListViewHolder) holder).setTitle(item.getPostTitle());
            ((NewsListViewHolder) holder).setDate(item.getDate());
            ((NewsListViewHolder) holder).setImage(item);
        }
    }

    @Override
    public int getItemCount() {
        // 添加一个脚部加载动画
        return super.getItemCount() + 1;
    }

    /**
     * 在里面判断每一个Item的类型，是没有图片还是单图片还是多图片
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        // 是底部动画直接返回
        if (position + 1 == getItemCount()) {
            return NewsItemViewType.FOOTER;
        }
        //默认有一张图片
        int type;
        NewsItem item = mData.get(position );
        ArrayList<String> imageList = item.getImageList();

        if (imageList == null || imageList.isEmpty()) {
            type = NewsItemViewType.NOIMAGE;
        }
        else if (imageList.size() == 3) {
            type = NewsItemViewType.THREEIMAGE;
        } else {
            type = NewsItemViewType.ONEIMAGE;
        }
        return type;
    }

    class FooterViewHolder extends BaseRecyclerViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
