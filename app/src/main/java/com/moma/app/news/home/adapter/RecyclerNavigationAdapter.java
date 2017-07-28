package com.moma.app.news.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.moma.app.news.R;
import com.moma.app.news.util.bean.NavigationItem;
import com.socks.library.KLog;

import java.util.List;


public class RecyclerNavigationAdapter extends BaseRecyclerAdapter<NavigationItem>{

    public RecyclerNavigationAdapter(Context context, List<NavigationItem> data) {
        super(context, data, null);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final BaseRecyclerViewHolder holder =
                new RecyclerNavigationViewHolder(mContext,
                        mInflater.inflate(R.layout.item_navigation, parent, false));

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

        NavigationItem item = mData.get(position);

        ((RecyclerNavigationViewHolder) holder).itemTextView.setText(item.getItemTitle());
        ((RecyclerNavigationViewHolder) holder).itemImageView.setImageResource(item.getItemImageId());
    }



}
