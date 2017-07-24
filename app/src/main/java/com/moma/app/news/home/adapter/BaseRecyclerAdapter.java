package com.moma.app.news.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.moma.app.news.api.bean.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moma on 17-7-24.
 */

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder>{
    protected OnItemClickListener mClickListener;
    protected Context mContext;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutInflater mInflater;
    protected List<NewsItem> mData;

    public BaseRecyclerAdapter(Context context, List<NewsItem> data, RecyclerView.LayoutManager layoutManager) {
        mContext = context;
        mLayoutManager = layoutManager;
        mData = data == null ? new ArrayList<NewsItem>() : data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public List<NewsItem> getmData() {
        return mData;
    }

    /**
     * 刷新数据时调用
     * @param mData
     */
    public void setmData(List<NewsItem> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    /**
     * 加载更多数据时调用
     * @param data
     */
    public void addMoreData(List<NewsItem> data) {
        int startPos = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(startPos, data.size());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

}
