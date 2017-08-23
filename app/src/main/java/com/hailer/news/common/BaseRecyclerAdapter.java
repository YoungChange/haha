package com.hailer.news.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerViewAdapter的基类
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

    /**
     * 在Recycle的上面插入一个item
     * @param data
     */
    public void addHeadData(T data){
        mData.add(0,data);
        notifyItemInserted(0);
        notifyItemRangeChanged(0,mData.size()-1);
    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }
}
