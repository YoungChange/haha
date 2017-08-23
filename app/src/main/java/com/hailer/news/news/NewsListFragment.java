package com.hailer.news.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.hailer.news.R;
import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.common.BaseRecycleViewDivider;
import com.hailer.news.common.LoadType;
import com.hailer.news.common.MaterialRefreshView;
import com.hailer.news.common.OnItemClickListener;
import com.hailer.news.newsdetail.NewsDetailActivity;
import com.hailer.news.util.MeasureUtil;
import com.hailer.news.util.annotation.ActivityFragmentInject;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moma on 17-7-17.
 *
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_news_list)
public class NewsListFragment extends Fragment{

    enum LOAD_STATE {
        UNLOAD, LOADING, COMPLATE
    }
    public static final String ARGS_NAME = "args_Name";
    public static final String ARGS_Id = "args_Id";

    private String mCatName;
    private String mCatId;

    private RecyclerRefreshLayout mRecyclerRefreshLayout;
    private RecyclerView mRecyclerView;
    private NewsListAdapter mAdapter;
    private Boolean mLoadingMore = false;
    private Boolean mIsRefreshing = false;
    protected View mFragmentRootView;
    protected int mContentViewId;
    private List mNewsList;
    private NewsContract.Presenter mPresenter;
    private NewsActivity mActivity;
    private ProgressBar mLoadingViewPb;
    private TextView mNoInternetTipTv;
    public static NewsListFragment newInstance(String catName, String catId) {
        Bundle args = new Bundle();
        args.putString(ARGS_NAME,catName);
        args.putString(ARGS_Id,catId);
        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCatName = getArguments().getString(ARGS_NAME);
            mCatId = getArguments().getString(ARGS_Id);
        }
        //mLoadState = LOAD_STATE.UNLOAD;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        mActivity = (NewsActivity)getActivity();
        return mFragmentRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoadingViewPb = view.findViewById(R.id.loading_view);
        mNoInternetTipTv = view.findViewById(R.id.no_internet_tip);
        initRecyclerView(view);
        initRecyclerRefreshLayout(view);
    }

    @Override
    public void onDestroyView() {
        //mRecyclerView.removeOnScrollListener();
        super.onDestroyView();
    }

    public void setPresenter(@NonNull NewsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void showNewsList(int loadType, List<NewsItem> list){
        mIsRefreshing = false;
        mNewsList = list;
        mRecyclerRefreshLayout.setEnabled(true);
        mRecyclerRefreshLayout.setRefreshing(false);
        mLoadingViewPb.setVisibility(View.GONE);
        mNoInternetTipTv.setVisibility(View.GONE);
        switch (loadType) {
            case LoadType.TYPE_REFRESH:
                mAdapter.setmData(list);
                break;
            case LoadType.TYPE_LOAD_MORE:
                if (list == null || list.size() == 0) {
                    //toast(getActivity().getString(R.string.all_loaded));
                    return;
                }
                mAdapter.addMoreData(list);
                break;
        }
    }

    protected void initRecyclerView(View view) {
        mRecyclerView = mFragmentRootView.findViewById(R.id.newslist_recycler_view);
        // 在Fragment中给RecyclerView增加滑动监听
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount() - 1;
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                //mLoading 防止多次加载同一批数据
                if (!mLoadingMore && totalItemCount < (lastVisibleItem + 2)) {
                    mLoadingMore = true;
                    mPresenter.loadMoreData(mCatId, totalItemCount);
                }
            }
        });

        //设置布局管理器
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //设置adapter
        mAdapter = new NewsListAdapter(getActivity(), null);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                NewsItem newsItem =  mAdapter.getmData().get(position);
                //KLog.e("onItemClick, position="+position+"; id="+newsItem.id+"; img="+newsItem.post_image);
                intent.putExtra("postid", String.valueOf(newsItem.getPostId()));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
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
    }

    protected void initRecyclerRefreshLayout(View view) {
        mRecyclerRefreshLayout = mFragmentRootView.findViewById(R.id.refresh_layout);
        // 允许拉动刷新
        mRecyclerRefreshLayout.setEnabled(false); // 第一次显示，先不允许下拉刷新
        //mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE);
        mRecyclerRefreshLayout.setRefreshStyle(RecyclerRefreshLayout.RefreshStyle.FLOAT);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                (int) MeasureUtil.dip2px(getActivity(), 40), (int)MeasureUtil.dip2px(getActivity(), 40));
        mRecyclerRefreshLayout.setRefreshView(new MaterialRefreshView(getActivity()), layoutParams);
        mRecyclerRefreshLayout.setOnRefreshListener(new RecyclerRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestRefresh(); // 下拉刷新内容
            }
        });
    }

    private void requestRefresh() {
        if (/*mInteractionListener != null && */!mIsRefreshing) {
            // 防止多次连续加载说数据
            mIsRefreshing = true;
            mActivity.getNewsList(mCatId);
            if (!haveData()) {
                mLoadingViewPb.setVisibility(View.VISIBLE);
            }
        }
    }

    public void showLoadError() {
        mRecyclerRefreshLayout.setEnabled(true);
        mNoInternetTipTv.setVisibility(View.VISIBLE);
    }
    public void display(){
        if (mAdapter != null) {
            requestRefresh();
        }
    }

    public boolean haveData() {
        if (mNewsList != null && mNewsList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

//    protected void showLoading() {
//        if (!haveData()) {
//        } else {
//
//            mLoadingViewPb.setVisibility(View.VISIBLE);
//        }
//    }

}