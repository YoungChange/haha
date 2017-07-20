package com.moma.app.news.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.moma.app.news.api.bean.NewsList;
import com.moma.app.news.base.BaseFragment;
import com.moma.app.news.R;
import com.moma.app.news.base.BaseSpacesItemDecoration;
import com.moma.app.news.base.DataLoadType;
import com.moma.app.news.home.adapter.BaseRecyclerAdapter;
import com.moma.app.news.home.presenter.INewsListPresenter;
import com.moma.app.news.home.presenter.INewsListPresenterImpl;
import com.moma.app.news.home.view.INewsListView;
import com.moma.app.news.util.MeasureUtil;
import com.moma.app.news.util.annotation.ActivityFragmentInject;

import java.util.List;

/**
 * Created by moma on 17-7-17.
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_news_list,
        handleRefreshLayout = true)
public class SimpleFragment extends BaseFragment<INewsListPresenter> implements INewsListView {

    public static final String ARGS_TYPE = "args_tagType";
    public static final String ARGS_NAME = "args_tagName";

    private String tagType;
    private String tagName;

    private RecyclerView mRecyclerView;
    private RelativeLayout mRelativeLayout;
    private BaseRecyclerAdapter mAdapter;


    /**
     *
     * @param tType
     * @param tName tab的title
     * @return
     */
    public static SimpleFragment newInstance(String tType,String tName) {
        Bundle args = new Bundle();

        args.putString(ARGS_TYPE, tType);
        args.putString(ARGS_NAME,tName);
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
        }

    }



    @Override
    protected void initView(View fragmentRootView) {

//        mLoadingView = (ThreePointLoadingView) fragmentRootView.findViewById(R.id.tpl_view);
//        mLoadingView.setOnClickListener(this);

        mRecyclerView = (RecyclerView) fragmentRootView.findViewById(R.id.recycler_view);

        mRelativeLayout = (RelativeLayout) fragmentRootView.findViewById(R.id.refresh_layout);

        mPresenter = new INewsListPresenterImpl(this,tagType , tagName);

    }


    @Override
    public void updateNewsList(final List<NewsList> data, String errorMsg, @DataLoadType.DataLoadTypeChecker int type) {
        Toast.makeText(getActivity(), "data大小:"+data.size(), Toast.LENGTH_SHORT).show();

        if (mAdapter == null) {
            initNewsList(data);
        }





    }



    private void initNewsList(final List<NewsList> data) {

        mAdapter = new BaseRecyclerAdapter(getActivity(), data);

        //设置布局管理器
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.addItemDecoration(new BaseSpacesItemDecoration(MeasureUtil.dip2px(getActivity(), 4)));

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(250);
        mRecyclerView.getItemAnimator().setMoveDuration(250);
        mRecyclerView.getItemAnimator().setChangeDuration(250);
        mRecyclerView.getItemAnimator().setRemoveDuration(250);

        //设置adapter
        mRecyclerView.setAdapter(mAdapter);




    }










//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.simple_fragment_layout,container,false);
//        TextView textView = (TextView) view.findViewById(R.id.textView);
//        textView.setText("第"+tagId+"页:"+tagName);
//        return view;
//    }
}
