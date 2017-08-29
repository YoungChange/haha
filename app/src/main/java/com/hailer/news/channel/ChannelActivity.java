package com.hailer.news.channel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.SparseArray;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hailer.news.R;
import com.hailer.news.common.ActivityCode;
import com.hailer.news.common.BaseActivity;
import com.hailer.news.common.Const;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.hailer.news.util.bean.ChannelInfo;
import com.socks.library.KLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by moma on 17-8-24.
 */

@ActivityFragmentInject(contentViewId = R.layout.activity_channel_manager,
        toolbarId = R.id.back_toolbar,
        toolbarBackImageButtonId = R.id.back_imagebutton,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.my_channel
)
public class ChannelActivity extends BaseActivity implements ChannelContract.View, ItemDragHelperCallBack.OnChannelDragListener{

    private Context mContext;
    private BaseActivity mActivity;
    private RecyclerView mChannelManagerRv;
    private ChannelAdapter mAdapter;
    private List<ChannelInfo> mChannelList;
    private ItemTouchHelper mHelper;
    public static void startChannelForResult(Context context, List<ChannelInfo> selectedDatas, List<ChannelInfo> unselectedDatas) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.Channel.DATA_SELECTED, (Serializable) selectedDatas);
        bundle.putSerializable(Const.Channel.DATA_UNSELECTED, (Serializable) unselectedDatas);
        Intent intent = new Intent(context, ChannelActivity.class);
        intent.putExtra("data", bundle);
        if (context instanceof Activity) {
            ((Activity)context).startActivityForResult(intent, Const.Activity.START_CHANNEL_FOR_RESULE);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        mChannelManagerRv = (RecyclerView) findViewById(R.id.change_channel_rv);
        mChannelList = new ArrayList<>();
        initView();
    }

    private void initView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        mChannelManagerRv.setLayoutManager(layoutManager);
        mChannelManagerRv.addItemDecoration(new ChannelItemDivider(this));
        mChannelList.add(new ChannelInfo("我的频道", ChannelInfo.TYPE_MY_CHANNEL));
        Bundle bundle = getIntent().getBundleExtra("data");
        List<ChannelInfo> selectChannelList = (List<ChannelInfo>) bundle
                .getSerializable(Const.Channel.DATA_SELECTED);
        mChannelList.addAll(selectChannelList);
        mChannelList.add(new ChannelInfo("更多频道", ChannelInfo.TYPE_OTHER_CHANNLE));
        List<ChannelInfo> unSelectChannelList = (List<ChannelInfo>) bundle
                .getSerializable(Const.Channel.DATA_UNSELECTED);
        mChannelList.addAll(unSelectChannelList);

        mAdapter = new ChannelAdapter(this, mChannelList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mChannelManagerRv.setLayoutManager(gridLayoutManager);
        mChannelManagerRv.setAdapter(mAdapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int group = mAdapter.getItemViewType(position);
                return (group == ChannelInfo.TYPE_MY_CHANNEL || group == ChannelInfo.TYPE_OTHER_CHANNLE) ? 4 : 1;
            }
        });

        ItemDragHelperCallBack dragHelperCallBack = new ItemDragHelperCallBack(this);
        mHelper = new ItemTouchHelper(dragHelperCallBack);
        mAdapter.setOnChannelDragListener(this);
        mHelper.attachToRecyclerView(mChannelManagerRv);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.back_imagebutton:
                this.finish();
                break;
            default:
                toast(this.getString(R.string.unknow_error));
        }
    }

    @Override
    public void showChannel(ArrayList<String> channelList) {

    }

    @Override
    public void onStarDrag(BaseViewHolder baseViewHolder) {
        //开始拖动
        KLog.i("开始拖动");
        mHelper.startDrag(baseViewHolder);
    }

    @Override
    public void onItemMove(int starPos, int endPos) {
        //我的频道之间移动
        onMove(starPos, endPos);
    }

    @Override
    public void onMoveToMyChannel(int starPos, int endPos) {
        //移动到我的频道
        onMove(starPos, endPos);
    }

    @Override
    public void onMoveToOtherChannel(int starPos, int endPos) {
        //移动到推荐频道
        onMove(starPos, endPos);
    }

    private void onMove(int starPos, int endPos) {
        ChannelInfo startChannel = mChannelList.get(starPos);
        //先删除之前的位置
        mChannelList.remove(starPos);
        //添加到现在的位置
        mChannelList.add(endPos, startChannel);
        mAdapter.notifyItemMoved(starPos, endPos);
    }
}
