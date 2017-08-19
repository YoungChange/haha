package com.hailer.news.comments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hailer.news.R;
import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.common.BaseRecycleViewDivider;
import com.hailer.news.common.BaseActivity;
import com.hailer.news.login.LoginActivity;
import com.hailer.news.newsdetail.NewsDetailActivity;
import com.hailer.news.util.MeasureUtil;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.socks.library.KLog;

import java.util.List;

@ActivityFragmentInject(contentViewId = R.layout.activity_news_comment_list,
        toolbarId = R.id.back_toolbar,
        toolbarBackImageButtonId = R.id.back_imagebutton,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.news_comment
)
public class CommentsActivity extends BaseActivity implements CommentsContract.View{

    private String postId;
    private int commentCount;

    private CommentsListAdapter newsCommentListAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Boolean mLoading = false;
    private CommentsPresenter mCommentsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerView = (RecyclerView) findViewById(R.id.newscommentlist_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.comment_refresh_layout);
        postId = getIntent().getStringExtra("postId");
        commentCount = getIntent().getIntExtra("commentCount",0);
        KLog.e("-------CommentsListActivity------postId:"+postId+";------commentCount:"+commentCount);
        mCommentsPresenter = new CommentsPresenter(this);
        mCommentsPresenter.getCommentsList(postId);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_imagebutton:
                this.finish();
                break;
            default:
                toast(this.getString(R.string.unknow_error));
        }
    }

    @Override
    public void showCommentsList(List<CommentInfo> data,boolean isRefresh){
        mLoading = false;
        KLog.e("showCommentsList...");
        if (newsCommentListAdapter == null) {
            initCommentList(data);
        }

        if(isRefresh){
            newsCommentListAdapter.setmData(data);
        }else{
            if (data == null || data.size() == 0) {
                toast(this.getString(R.string.all_loaded));
                return;
            }
            newsCommentListAdapter.addMoreData(data);
        }
    }

    @Override
    public void showErrorMsg(int error){

    }

    @Override
    public void popLoginDlg() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent,1);
    }

    private void initCommentList(final List<CommentInfo> newsCommentList) {

        //刷新监听事件
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCommentsPresenter.refreshData();
                mSwipeRefreshLayout.setRefreshing(false);
                newsCommentListAdapter.notifyDataSetChanged();
            }
        });

        newsCommentListAdapter = new CommentsListAdapter(this, newsCommentList);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // 给RecyclerView增加滑动监听
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                //mLoading 防止多次加载同一批数据
                if (!mLoading && totalItemCount < (lastVisibleItem + 2)) {
                    mLoading = true;
                    mCommentsPresenter.loadMoreData();
                }
            }
        });

        //添加分割线
        mRecyclerView.addItemDecoration(
                new BaseRecycleViewDivider(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        MeasureUtil.dip2px(this,1),
                        getResources().getColor(R.color.divide_newslist)));

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(250);
        mRecyclerView.getItemAnimator().setMoveDuration(250);
        mRecyclerView.getItemAnimator().setChangeDuration(250);
        mRecyclerView.getItemAnimator().setRemoveDuration(250);

        mRecyclerView.setAdapter(newsCommentListAdapter);
    }

    public void vote(int id) {
        mCommentsPresenter.voteComment(id);
    }

    public void unVote(int id) {
        mCommentsPresenter.unVoteComment(id);
    }
}
