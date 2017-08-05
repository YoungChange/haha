package com.hailer.news.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hailer.news.R;
import com.hailer.news.util.GlideUtils;
import com.socks.library.KLog;


/**
 * Created by moma on 17-8-1.
 */

public class CommentsListViewHolder extends BaseRecyclerViewHolder{

    private ImageView commentUserPic;
    private TextView commentUserName;
    private TextView commentContent;
    private TextView commentTime;


    public CommentsListViewHolder(Context context, View itemView) {
        super(itemView);

        mContext = context;

        commentUserPic = itemView.findViewById(R.id.comment_userpicture);
        commentUserName = itemView.findViewById(R.id.comment_username);
        commentContent = itemView.findViewById(R.id.comment_content);
        commentTime = itemView.findViewById(R.id.comment_time);
    }


    public void setCommentUserPic(String userPicURL) {
        KLog.e("--------------, userPicURL="+ userPicURL);
        GlideUtils.loadDefault(userPicURL, commentUserPic, false, null, DiskCacheStrategy.RESULT);
    }

    public void setCommentUserName(String userName) {
        this.commentUserName.setText(userName);
    }

    public void setCommentContent(String content) {
        this.commentContent.setText(content);
    }

    public void setCommentTime(String time) {
        this.commentTime.setText(time);
    }
}
