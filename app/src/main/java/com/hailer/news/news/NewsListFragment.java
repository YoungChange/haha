package com.hailer.news.news;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hailer.news.R;
import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.common.BaseRecycleViewDivider;
import com.hailer.news.common.LoadType;
import com.hailer.news.common.OnItemClickListener;
import com.hailer.news.newsdetail.NewsDetailActivity;
import com.hailer.news.util.MeasureUtil;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.socks.library.KLog;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moma on 17-7-17.
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_news_list)
public class NewsListFragment extends Fragment{

    public static final String ARGS_NAME = "args_Name";
    public static final String ARGS_Id = "args_Id";

    private String mCatName;
    private String mCatId;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private NewsListAdapter mAdapter;

    private Boolean mLoading = false;

    protected View mFragmentRootView;
    protected int mContentViewId;

    private NewsContract.Presenter mPresenter;

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

        mSwipeRefreshLayout = (SwipeRefreshLayout) mFragmentRootView.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) mFragmentRootView.findViewById(R.id.newslist_recycler_view);

        return mFragmentRootView;
    }

    public void setPresenter(@NonNull NewsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void showNewsList(int loadType, List<NewsItem> list){
        mLoading = false;
        if (mAdapter == null) {
            initNewsList(list);
        }

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

    private void initNewsList(final List<NewsItem> data) {

        //刷新监听事件
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData(mCatId);
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.notifyDataSetChanged();
            }
        });


        mAdapter = new NewsListAdapter(getActivity(), data);
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


        //设置布局管理器
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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
                    mPresenter.loadMoreData(mCatId, totalItemCount);
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

        //设置adapter
        mRecyclerView.setAdapter(mAdapter);
    }

    public void refreshList(){
        mPresenter.refreshData(mCatId);
    }

    public void display(){
        if (mAdapter == null) {
            mPresenter.getNewsList(mCatId);
        }
    }
}
