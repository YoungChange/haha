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

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder>{
    protected OnItemClickListener mClickListener;
    protected Context mContext;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutInflater mInflater;
    protected List<T> mData;

    public BaseRecyclerAdapter(Context context, List<T> data, RecyclerView.LayoutManager layoutManager) {
        mContext = context;
        mLayoutManager = layoutManager;
        mData = data == null ? new ArrayList<T>() : data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public List<T> getmData() {
        return mData;
    }

    /**
     * 刷新数据时调用
     * @param mData
     */
    public void setmData(List<T> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    /**
     * 加载更多数据时调用
     * @param data
     */
    public void addMoreData(List<T> data) {
        int startPos = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(startPos, data.size());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

}
