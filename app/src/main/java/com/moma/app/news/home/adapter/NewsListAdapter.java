package com.moma.app.news.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.moma.app.news.R;
import com.moma.app.news.api.bean.NewsItem;
import com.socks.library.KLog;

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
        final ListItemViewHolder holder;
        View view;

        if(viewType == ItemViewType.THREEIMAGE){
            view =  mInflater.inflate(R.layout.item_news_three_image, parent, false);
        }else if(viewType == ItemViewType.ONEIMAGE){
            view =  mInflater.inflate(R.layout.item_news_one_image, parent, false);
        }else{
            view =  mInflater.inflate(R.layout.item_news_no_image, parent, false);
        }
        holder = new ListItemViewHolder(mContext, view, viewType);

        if (mClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(holder.getLayoutPosition()!= RecyclerView.NO_POSITION){
                        try{
                            mClickListener.onItemClick(view,holder.getLayoutPosition());
                        }catch(Exception e){
                            KLog.d("设置ItemView 监听点击事件失败");
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        NewsItem item = mData.get(position);

        ((ListItemViewHolder) holder).setTitle(item.post_title);
        ((ListItemViewHolder) holder).setDate(item.post_date);
        ((ListItemViewHolder) holder).setImage(item);

        //直接使用Glide
        //Glide.with(mContext).load(item.imgsrc).placeholder(R.drawable.ic_loading).into(holder.news_summary_photo);
    }



    /**
     * 在里面判断每一个Item的类型，是没有图片还是单图片还是多图片
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //默认有一张图片
        int type = ItemViewType.ONEIMAGE;

        NewsItem item = mData.get(position);
        if (item.post_image_list != null && item.post_image_list.size()>0) {
            type = ItemViewType.THREEIMAGE;
        } else if (item.post_image == null || item.post_image.size() == 0) {
            type = ItemViewType.NOIMAGE;
        }

        return type;

//        if(mData.get(position).imgextra.size() == 0){
//            return ItemViewType.NOIMAGE;
//        }else if (mData.get(position).imgextra.size() == 1){
//            return ItemViewType.ONEIMAGE;
//        }else if(mData.get(position).imgextra.size() == 2){
//            return ItemViewType.TWOIMAGE;
//        }else{
//            return ItemViewType.THREEIMAGE;
//        }
    }





}
