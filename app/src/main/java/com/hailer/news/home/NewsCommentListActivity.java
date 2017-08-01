package com.hailer.news.home;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hailer.news.R;
import com.hailer.news.base.BaseActivity;
import com.hailer.news.base.BaseRecycleViewDivider;
import com.hailer.news.base.DataLoadType;
import com.hailer.news.home.adapter.NewsCommentListAdapter;
import com.hailer.news.home.presenter.INewsCommentListPresenter;
import com.hailer.news.home.presenter.INewsCommentListPresenterImpl;
import com.hailer.news.home.view.INewsCommentListView;
import com.hailer.news.util.MeasureUtil;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.hailer.news.util.bean.NewsComment;

import java.util.List;

@ActivityFragmentInject(contentViewId = R.layout.activity_news_comment_list,
        toolbarId = R.id.back_toolbar,
        toolbarBackImageButtonId = R.id.back_imagebutton,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.news_comment
)
public class NewsCommentListActivity extends BaseActivity<INewsCommentListPresenter> implements INewsCommentListView{

    private String postId;

    private NewsCommentListAdapter newsCommentListAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Boolean mLoading = false;

    @Override
    protected void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.newscommentlist_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        postId = getIntent().getStringExtra("postId");
        mPresenter = new INewsCommentListPresenterImpl(this,postId);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_imagebutton:
                this.finish();
                break;
            default:
                toast(this.getString(R.string.what_you_did));
        }
    }

    @Override
    public void updateNewsCommentList(List<NewsComment> data, @NonNull String errorMsg, @DataLoadType.DataLoadTypeChecker int type) {

//        NewsComment newsCommentItem = new NewsComment("2017-9-8 8:8:8","这个问题困扰")



        mLoading = false;

        if (newsCommentListAdapter == null) {
            initCommentList(data);
        }

        switch (type) {
            case DataLoadType.TYPE_REFRESH_SUCCESS:
                newsCommentListAdapter.setmData(data);
                toast(this.getString(R.string.refresh_success));
                break;
            case DataLoadType.TYPE_REFRESH_FAIL:
                newsCommentListAdapter.notifyDataSetChanged();
                break;
            case DataLoadType.TYPE_LOAD_MORE_SUCCESS:
                if (data == null || data.size() == 0) {
                    toast(this.getString(R.string.all_loaded));
                    return;
                }
                newsCommentListAdapter.addMoreData(data);
                break;
            case DataLoadType.TYPE_LOAD_MORE_FAIL:
                toast(this.getString(R.string.load_more_fail));
                break;
        }
    }

    private void initCommentList(final List<NewsComment> newsCommentList) {

        //刷新监听事件
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData();
                mSwipeRefreshLayout.setRefreshing(false);
                newsCommentListAdapter.notifyDataSetChanged();
            }
        });

        newsCommentListAdapter = new NewsCommentListAdapter(this, newsCommentList);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // 在Fragment中给RecyclerView增加滑动监听
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
                    mPresenter.loadMoreData();
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
}
