package com.hailer.news.newsdetailandcomment;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hailer.news.R;
import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.comments.CommentsActivity;
import com.hailer.news.common.BaseRecyclerViewHolder;
import com.hailer.news.util.FuncUtil;
import com.hailer.news.util.GlideUtils;


/**
 * Created by moma on 17-8-1.
 */

public class CommentsListViewHolder extends BaseRecyclerViewHolder {
    private NewsDetailAddCommentActivity mNewsDetailAddCommentActivity;
    private ImageView commentUserPic;
    private TextView commentUserName;
    private TextView commentContent;
    private TextView commentTime;
    public TextView addOneTv;// +1

    private RelativeLayout mLlVoteContainer;
    private ImageButton mIbVote;
    private TextView mTvVoteCount;
    private CommentInfo mCommentInfo;
    private int mVoteTextColor;
    private int mUnVoteTextColor;
    private BaseRecyclerViewHolder viewHolder;


    public CommentsListViewHolder(Context context, View itemView) {
        super(itemView);
        viewHolder = this;
        mNewsDetailAddCommentActivity = (NewsDetailAddCommentActivity)context;
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

        addOneTv = itemView.findViewById(R.id.add_one_tv);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCommentInfo.isVoted()) {
                    mLlVoteContainer.setClickable(false);
                    mNewsDetailAddCommentActivity.vote(mCommentInfo,viewHolder);
                }
            }
        };
        mLlVoteContainer.setOnClickListener(clickListener);
    }


    public void addOneAnim(){
        Animation animation = AnimationUtils.loadAnimation(mNewsDetailAddCommentActivity, R.anim.add_score_anim);
        addOneTv.setVisibility(View.VISIBLE);
        addOneTv.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                addOneTv.setVisibility(View.GONE);
            }
        }, 1000);
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
            commentTime.setText(FuncUtil.time2Time(mCommentInfo.getDate()));
            //boolean isVoted = CommentVoteUtil.getInstances(mContext).isVoted(commentInfo.getId());
            //commentInfo.setVote(isVoted);
            setVote(mCommentInfo.isVoted());
            mLlVoteContainer.setClickable(true);
        }
        return this;
    }

}
