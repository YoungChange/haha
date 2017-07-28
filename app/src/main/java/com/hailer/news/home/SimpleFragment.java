package com.hailer.news.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.base.BaseFragment;
import com.moma.app.news.R;
import com.hailer.news.base.BaseRecycleViewDivider;
import com.hailer.news.base.DataLoadType;
import com.hailer.news.home.adapter.NewsListAdapter;
import com.hailer.news.home.adapter.OnItemClickListener;
import com.hailer.news.home.presenter.INewsListPresenter;
import com.hailer.news.home.presenter.INewsListPresenterImpl;
import com.hailer.news.home.view.INewsListView;
import com.hailer.news.util.MeasureUtil;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by moma on 17-7-17.
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_news_list,
        handleRefreshLayout = true)
public class SimpleFragment extends BaseFragment<INewsListPresenter> implements INewsListView {

    public static final String ARGS_TYPE = "args_tagType";
    public static final String ARGS_NAME = "args_tagName";
    public static final String ARGS_Id = "args_tagId";

    private String tagType;
    private String tagName;
    private String tagId;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RelativeLayout mRelativeLayout;
    private NewsListAdapter mAdapter;

    private Boolean mLoading = false;


    /**
     *
     * @param tagType
     * @param tagName
     * @param tagId
     * @return
     */
    public static SimpleFragment newInstance(String tagType,String tagName,String tagId) {
        Bundle args = new Bundle();

        args.putString(ARGS_TYPE,tagType);
        args.putString(ARGS_NAME,tagName);
        args.putString(ARGS_Id,tagId);
        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tagType = getArguments().getString(ARGS_TYPE);
            tagName = getArguments().getString(ARGS_NAME);
            tagId = getArguments().getString(ARGS_Id);
        }

    }

    @Override
    protected void initView(View fragmentRootView) {

        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentRootView.findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) fragmentRootView.findViewById(R.id.newslist_recycler_view);
        mPresenter = new INewsListPresenterImpl(this,tagId,tagType);
    }

    @Override
    public void updateNewsList(final List<NewsItem> data, String errorMsg, @DataLoadType.DataLoadTypeChecker int type) {

        mLoading = false;

        if (mAdapter == null) {
            initNewsList(data);
        }

        switch (type) {
            case DataLoadType.TYPE_REFRESH_SUCCESS:
                mAdapter.setmData(data);
                toast(getActivity().getString(R.string.refresh_success));
                break;
            case DataLoadType.TYPE_REFRESH_FAIL:
                mAdapter.notifyDataSetChanged();
                break;
            case DataLoadType.TYPE_LOAD_MORE_SUCCESS:
                if (data == null || data.size() == 0) {
                    toast(getActivity().getString(R.string.all_loaded));
                    return;
                }
                mAdapter.addMoreData(data);
                break;
            case DataLoadType.TYPE_LOAD_MORE_FAIL:
                toast(getActivity().getString(R.string.load_more_fail));
                break;
        }

    }



    private void initNewsList(final List<NewsItem> data) {

        //刷新监听事件
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData();
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
                KLog.e("onItemClick, position="+position+"; id="+newsItem.id+"; img="+newsItem.post_image);
                intent.putExtra("postid", String.valueOf(newsItem.id));
                //intent.putExtra("imgsrc", newsItem.post_image);

                try{
                    startActivity(intent);
                }catch(Exception e){
                    e.printStackTrace();
                }
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

        //设置adapter
        mRecyclerView.setAdapter(mAdapter);
    }
}
