package com.hailer.news.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hailer.news.R;
import com.hailer.news.util.bean.NewsComment;

import java.util.List;

/**
 * Created by moma on 17-8-1.
 */

public class NewsCommentListAdapter extends BaseRecyclerAdapter<NewsComment>{

    public NewsCommentListAdapter(Context context, List<NewsComment> data){
        super(context, data, null);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item_comment, parent, false);
        final NewsCommentListViewHolder holder = new NewsCommentListViewHolder(mContext, view);

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        NewsComment item = mData.get(position);

        ((NewsCommentListViewHolder) holder).setCommentUserPic(item.getUserPicUrl());
        ((NewsCommentListViewHolder) holder).setCommentUserName(item.getUserName());
        ((NewsCommentListViewHolder) holder).setCommentContent(item.getContent());
        ((NewsCommentListViewHolder) holder).setCommentTime(item.getTime());

    }


}
