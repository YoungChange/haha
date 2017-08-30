package com.hailer.news.channel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hailer.news.R;
import com.hailer.news.common.BaseActivity;
import com.hailer.news.common.Const;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.hailer.news.util.bean.ChannelInfo;
import com.socks.library.KLog;
import java.io.Serializable;
import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private ChannelContract.Presenter mPresenter;
    private RecyclerView mChannelManagerRv;
    private ChannelAdapter mAdapter;
    private List<ChannelInfo> mChannelList;
    private ItemTouchHelper mHelper;
    private GridLayoutManager mLayoutManager;

    public static void startChannelForResult(Context context) {
        Intent intent = new Intent(context, ChannelActivity.class);
        if (context instanceof Activity) {
            ((Activity)context).startActivityForResult(intent, Const.Activity.START_CHANNEL_FOR_RESULE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChannelManagerRv = (RecyclerView) findViewById(R.id.change_channel_rv);
        mChannelList = new ArrayList<>();
        mPresenter = new ChannelPresenter(this);
        initView();
        mPresenter.getChannels();
    }

    private void initView() {
        // 我在将ChowChannel中部分视图初始化放到此处时，会引起视图的混乱。哪些可以在数据加载
        // 前初始化有待考察。
        mLayoutManager = new GridLayoutManager(this, 4);
        mChannelManagerRv.setLayoutManager(mLayoutManager);
        mChannelManagerRv.addItemDecoration(new ChannelItemDivider(this));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.back_imagebutton:
                this.onBackPressed();
                break;
            default:
                toast(this.getString(R.string.unknow_error));
        }
    }

    @Override
    public void showChannel(List<ChannelInfo> channelList, List<ChannelInfo> otherChannelList) {

        mChannelList.add(new ChannelInfo("我的频道", ChannelInfo.TYPE_MY_CHANNEL));
        mChannelList.addAll(channelList);
        mChannelList.add(new ChannelInfo("更多频道", ChannelInfo.TYPE_OTHER_CHANNEL));
        for (ChannelInfo info : otherChannelList) {
            info.setItemType(ChannelInfo.TYPE_OTHER_CHANNEL_ITEM);
        }
        mChannelList.addAll(otherChannelList);

        mAdapter = new ChannelAdapter(this, mChannelList);
        mChannelManagerRv.setLayoutManager(mLayoutManager);
        mChannelManagerRv.setAdapter(mAdapter);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int group = mAdapter.getItemViewType(position);
                return (group == ChannelInfo.TYPE_MY_CHANNEL || group == ChannelInfo.TYPE_OTHER_CHANNEL) ? 4 : 1;
            }
        });

        ItemDragHelperCallBack dragHelperCallBack = new ItemDragHelperCallBack(this);
        mHelper = new ItemTouchHelper(dragHelperCallBack);
        mAdapter.setOnChannelDragListener(this);
        mHelper.attachToRecyclerView(mChannelManagerRv);
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

    @Override
    public void onBackPressed() {
        Iterator<ChannelInfo> channels = mChannelList.iterator();
        while (channels.hasNext()) {
            // 删除标题
            ChannelInfo info = channels.next();
            if (info.getItemType() == ChannelInfo.TYPE_MY_CHANNEL ||
                    info.getItemType() == ChannelInfo.TYPE_OTHER_CHANNEL) {
                channels.remove();
            }
        }
        mPresenter.updateChannel(mChannelList);

        ArrayList selectChannel = new ArrayList();
        for (ChannelInfo info : mChannelList) {
            if (info.getItemType() == ChannelInfo.TYPE_MY_CHANNEL_ITEM) {
                selectChannel.add(info);
            }
        }
        Intent intent = new Intent();
        intent.putExtra(Const.Channel.SELECT_CHANNEL_LIST, selectChannel);
        setResult(Const.Activity.RESPONSE_CODE_FROM_CHANNEL, intent);
        super.onBackPressed();
    }
}
