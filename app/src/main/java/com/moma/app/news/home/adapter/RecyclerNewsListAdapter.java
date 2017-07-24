package com.moma.app.news.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.moma.app.news.R;
import com.moma.app.news.api.bean.NewsItem;
import com.moma.app.news.util.GlideUtils;
import com.socks.library.KLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by moma on 17-7-20.
 */

public  class RecyclerNewsListAdapter extends BaseRecyclerAdapter {

    public RecyclerNewsListAdapter(Context context, List<NewsItem> data) {
        super(context, data, null);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseRecyclerViewHolder holder;
        if(viewType == ItemViewType.NOIMAGE){
            holder = new RecyclerNoImageViewHolder(mContext, mInflater.inflate(R.layout.item_news_no_image, parent, false));
        }else if(viewType == ItemViewType.ONEIMAGE){
            holder = new RecyclerOneImageViewHolder(mContext, mInflater.inflate(R.layout.item_news_one_image, parent, false));
        }else{
            holder = new RecyclerThreeImageViewHolder(mContext, mInflater.inflate(R.layout.item_news_three_image, parent, false));
        }

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

        if(holder instanceof  RecyclerNoImageViewHolder){
            ((RecyclerNoImageViewHolder) holder).news_summary_title.setText(item.post_title);
            ((RecyclerNoImageViewHolder) holder).news_summary_time.setText(time2Time(item.post_date));
        }else if(holder instanceof  RecyclerOneImageViewHolder){
            ((RecyclerOneImageViewHolder) holder).news_summary_title.setText(item.post_title);
            ((RecyclerOneImageViewHolder) holder).news_summary_time.setText(time2Time(item.post_date));
            GlideUtils.loadDefault(item.post_image, ((RecyclerOneImageViewHolder) holder).news_summary_photo, null, null, DiskCacheStrategy.RESULT);
        }else{
            ((RecyclerThreeImageViewHolder) holder).news_summary_title.setText(item.post_title);
            ((RecyclerThreeImageViewHolder) holder).news_summary_time.setText(time2Time(item.post_date));
            GlideUtils.loadDefault(item.post_image, ((RecyclerThreeImageViewHolder) holder).news_summary_photo1, null, null, DiskCacheStrategy.RESULT);
            GlideUtils.loadDefault(item.post_image, ((RecyclerThreeImageViewHolder) holder).news_summary_photo2, null, null, DiskCacheStrategy.RESULT);
            GlideUtils.loadDefault(item.post_image, ((RecyclerThreeImageViewHolder) holder).news_summary_photo3, null, null, DiskCacheStrategy.RESULT);
        }

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
        int type = position % 3;
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


    /**
     * 求yyyy-MM-dd HH:mm:ss格式的时间 字符串 与当前时间的间隔
     * @param str yyyy-MM-dd HH:mm:ss格式的时间
     * @return
     */
    public String time2Time(String str){

        String returnValue;

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;

        SimpleDateFormat simpleDateFormat  =   new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date newsdate = simpleDateFormat.parse(str);
            Date nowDate  = new Date();
            // 获得两个时间的毫秒时间差异
            long diff = nowDate.getTime() - newsdate.getTime();
            // 计算差多少天
            long day = diff / nd;
            // 计算差多少小时
            long hour = diff % nd / nh;
            // 计算差多少分钟
            long min = diff % nd % nh / nm;

            if(day != 0){
                returnValue = (day+"天前");
            }else if(hour != 0){
                returnValue = (hour+"小時前");
            }else{
                returnValue = (min+"分鐘前");
            }

        }catch(ParseException e){
            returnValue = str;
        }

        return returnValue;
    }


}
