package com.moma.app.news.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.moma.app.news.R;
import com.moma.app.news.api.bean.NewsList;
import com.moma.app.news.util.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moma on 17-7-20.
 */

public  class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {


    protected List<NewsList> mData;
    protected Context mContext;
    private RecyclerView.LayoutManager mLayoutManager;
    protected LayoutInflater mInflater;

    public BaseRecyclerAdapter(Context context, List<NewsList> data) {
        this(context, data, null);
    }

    public BaseRecyclerAdapter(Context context, List<NewsList> data, RecyclerView.LayoutManager layoutManager) {
        mContext = context;
        mLayoutManager = layoutManager;
        mData = data == null ? new ArrayList<NewsList>() : data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder holder = new BaseRecyclerViewHolder(mContext, mInflater.inflate(R.layout.item_news_summary, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
//        bindData(holder,position,mData.get(position));
        NewsList item = mData.get(position);
//        使用GlideUtils
//        GlideUtils.loadDefault(item.imgsrc, holder.news_summary_photo, null, null, DiskCacheStrategy.RESULT);
//        直接使用Glide
        Glide.with(mContext).load(item.imgsrc).placeholder(R.drawable.ic_loading).into(holder.news_summary_photo);
        holder.news_summary_digest.setText(item.digest);
        holder.news_summary_title.setText(item.title);
        holder.news_summary_ptime.setText(item.ptime);
    }


    public void bindData(BaseRecyclerViewHolder holder, int position, NewsList item){

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


}
