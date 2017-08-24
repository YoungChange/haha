package com.hailer.news.newsdetailandcomment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.hailer.news.R;
import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.common.BaseRecyclerAdapter;
import com.hailer.news.common.BaseRecyclerViewHolder;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by moma on 17-8-1.
 */

public class CommentsListAdapter extends BaseRecyclerAdapter<CommentInfo> {
    public CommentsListAdapter(Context context, List<CommentInfo> data){
        super(context, data, null);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item_comment, parent, false);
        final CommentsListViewHolder holder = new CommentsListViewHolder(mContext, view);

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        CommentInfo item = mData.get(position);
        KLog.e("CommentsListAdapter---onBindViewHolder---CommentInfo.isVoted:"+item.isVoted());

        ((CommentsListViewHolder) holder).setData(item);
    }


    public void resetVote(int position) {
        notifyItemChanged(position);
    }
}
