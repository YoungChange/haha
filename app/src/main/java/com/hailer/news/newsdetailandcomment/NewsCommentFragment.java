package com.hailer.news.newsdetailandcomment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hailer.news.R;
import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.common.BaseRecycleViewDivider;
import com.hailer.news.common.BaseRecyclerViewHolder;
import com.hailer.news.util.MeasureUtil;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.socks.library.KLog;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moma on 17-8-23.
 */

@ActivityFragmentInject(contentViewId = R.layout.fragment_comment_list)
public class NewsCommentFragment extends Fragment {

    protected View mFragmentRootView;
    private NewsDetailAddCommentContract.Presenter mPresenter;
    protected int mContentViewId;

    private CommentsListAdapter newsCommentListAdapter;
    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Boolean mLoading = false;

    private int mViewHolderPosition;

    private BaseRecyclerViewHolder mViewHolder;

    private String mPostId;
    private String mPostUrl;
    private String mPostTitle;
    private static final String ARGS_POST_ID = "args_PostId";
    private static final String ARGS_POST_URL = "args_PostUrl";
    private static final String ARGS_POST_TITLE = "args_PostTiTle";

    public static NewsCommentFragment newInstance(String postId,String postUrl,String postTitle) {
        Bundle args = new Bundle();
        args.putString(ARGS_POST_ID,postId);
        args.putString(ARGS_POST_URL,postUrl);
        args.putString(ARGS_POST_TITLE,postTitle);
        NewsCommentFragment fragment = new NewsCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPostId = getArguments().getString(ARGS_POST_ID);
            mPostUrl = getArguments().getString(ARGS_POST_URL);
            mPostTitle = getArguments().getString(ARGS_POST_TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mFragmentRootView == null) {
            if (getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
                ActivityFragmentInject annotation = getClass()
                        .getAnnotation(ActivityFragmentInject.class);
                mContentViewId = annotation.contentViewId();

            } else {
                throw new RuntimeException(
                        "Class must add annotations of ActivityFragmentInitParams.class");
            }

            try{
                mFragmentRootView = inflater.inflate(mContentViewId, container, false);
            }catch (Exception e){
                throw e;
            }
        }

        mRecyclerView = mFragmentRootView.findViewById(R.id.newscommentlist_recycler_view);
        mSwipeRefreshLayout = mFragmentRootView.findViewById(R.id.comment_refresh_layout);

        mPresenter.getCommentsList(mPostId);

        return mFragmentRootView;
    }

    public void setPresenter(@NonNull NewsDetailAddCommentContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    public void showCommentsList(List<CommentInfo> data,boolean isRefresh){
        KLog.e("-----List<CommentInfo>------:"+data.size());
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


    private void initCommentList(final List<CommentInfo> newsCommentList) {

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

        newsCommentListAdapter = new CommentsListAdapter(getActivity(), newsCommentList);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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
                    mPresenter.loadMoreData();
                }
            }
        });

        //添加分割线
        mRecyclerView.addItemDecoration(
                new BaseRecycleViewDivider(
                        getActivity(),
                        LinearLayoutManager.HORIZONTAL,
                        MeasureUtil.dip2px(getActivity(),1),
                        getResources().getColor(R.color.divide_newslist)));

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(250);
        mRecyclerView.getItemAnimator().setMoveDuration(250);
        mRecyclerView.getItemAnimator().setChangeDuration(250);
        mRecyclerView.getItemAnimator().setRemoveDuration(250);

        mRecyclerView.setAdapter(newsCommentListAdapter);
    }

    public void vote(CommentInfo commentInfo,BaseRecyclerViewHolder viewHolder) {
        mPresenter.voteComment(commentInfo);
        mViewHolder = viewHolder;
        mViewHolderPosition = viewHolder.getAdapterPosition();
    }

    public void resetVote() {
        //newsCommentListAdapter.resetVote(mViewHolderPosition);
    }

    private Toast mToast;
    public void toast(String msg) {

        if(mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        KLog.e("---------Which Activity："+this.getClass().getName());
        if(mToast!=null){
            mToast.cancel();
        }
    }
}
