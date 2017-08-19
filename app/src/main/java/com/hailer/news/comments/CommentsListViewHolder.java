package com.hailer.news.comments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hailer.news.R;
import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.common.BaseRecyclerViewHolder;
import com.hailer.news.util.GlideUtils;
import com.socks.library.KLog;


/**
 * Created by moma on 17-8-1.
 */

public class CommentsListViewHolder extends BaseRecyclerViewHolder {
    private CommentsActivity mCommentsActivity;
    private ImageView commentUserPic;
    private TextView commentUserName;
    private TextView commentContent;
    private TextView commentTime;
    private LinearLayout mLlVoteContainer;
    private ImageButton mIbVote;
    private TextView mTvVoteCount;
    private CommentInfo mCommentInfo;
    //private boolean mIsVoted;
    private int mVoteTextColor;
    private int mUnVoteTextColor;
    public CommentsListViewHolder(Context context, View itemView) {
        super(itemView);
        mCommentsActivity = (CommentsActivity)context;
        mContext = context;
        commentUserPic = itemView.findViewById(R.id.comment_userpicture);
        commentUserName = itemView.findViewById(R.id.comment_username);
        commentContent = itemView.findViewById(R.id.comment_content);
        commentTime = itemView.findViewById(R.id.comment_time);
        mLlVoteContainer = itemView.findViewById(R.id.ll_vote_container);
        mIbVote = itemView.findViewById(R.id.ib_vote);
        mTvVoteCount = itemView.findViewById(R.id.tv_vote_count);
        mVoteTextColor = Color.rgb(0xf2, 0x44, 0x44);
        mUnVoteTextColor = Color.rgb(0x80, 0x80, 0x80);

        mLlVoteContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (! mCommentInfo.isVoted()) {
                    mCommentsActivity.vote(mCommentInfo);
                }
//                else {
//                }
            }
        });
    }

    public CommentsListViewHolder setVote(boolean voted) {
        if (voted) {
            mIbVote.setImageResource(R.drawable.liked);
            mTvVoteCount.setTextColor(mVoteTextColor);
        }
        else  {
            mIbVote.setImageResource(R.drawable.like);
            mTvVoteCount.setTextColor(mUnVoteTextColor);
        }
        mTvVoteCount.setText(Integer.toString(mCommentInfo.getCommentLike()));
        return this;
    }

    public CommentsListViewHolder setData(CommentInfo commentInfo) {
        if (commentInfo != null) {
            mCommentInfo = commentInfo;
            GlideUtils.loadDefault(mCommentInfo.getUserAvatar(), commentUserPic,
                    false, null, DiskCacheStrategy.RESULT);
            commentUserName.setText(mCommentInfo.getUserName());
            commentContent.setText(mCommentInfo.getComment());
            commentTime.setText(mCommentInfo.getDate());

            setVote(mCommentInfo.isVoted());
        }
        return this;
    }

}
