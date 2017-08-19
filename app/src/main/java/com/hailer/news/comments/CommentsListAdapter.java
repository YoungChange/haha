package com.hailer.news.comments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.hailer.news.R;
import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.common.BaseRecyclerAdapter;
import com.hailer.news.common.BaseRecyclerViewHolder;

import java.util.List;

/**
 * Created by moma on 17-8-1.
 */

public class CommentsListAdapter extends BaseRecyclerAdapter<CommentInfo> {
    //private CommentsActivity mCommentsActivity;
    public CommentsListAdapter(Context context, List<CommentInfo> data){
        super(context, data, null);
        //mCommentsActivity = (CommentsActivity)context;
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
        ((CommentsListViewHolder) holder).setData(item);
               /* setCommentUserPic(item.getUserAvatar())
                .setCommentUserName(item.getUserName())
                .setCommentContent(item.getComment())
                .setCommentTime(item.getDate());*/
    }




}
