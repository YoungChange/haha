package com.hailer.news.channel;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hailer.news.R;
import com.hailer.news.util.bean.ChannelInfo;
import java.util.List;

/**
 * Created by moma on 2017/8/26 0005.
 */

public class ChannelAdapter extends BaseMultiItemQuickAdapter<ChannelInfo> {
    private static final int TYPE_MY_CHANNEL = 1;
    private static final int TYPE_MY_CHANNEL_ITEM = 2;
    private static final int TYPE_OTHER_CHANNLE = 3;
    private static final int TYPE_OTHER_CHANNEL_ITEM = 4;
    private BaseViewHolder mEditViewHolder;
    private boolean mIsEdit;
    private long startTime;
    // touch 间隔时间  用于分辨是否是 "点击"
    private static final long SPACE_TIME = 100;
    private RecyclerView mRecyclerView;

    public ChannelAdapter(List<ChannelInfo> data) {
        super(data);
        //默认没有编辑
        mIsEdit = false;
        addItemType(TYPE_MY_CHANNEL, R.layout.channel_group_my);
        addItemType(TYPE_MY_CHANNEL_ITEM, R.layout.item_channel);
        addItemType(TYPE_OTHER_CHANNLE, R.layout.channel_group_orther);
        addItemType(TYPE_OTHER_CHANNEL_ITEM, R.layout.item_channel);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mRecyclerView = (RecyclerView) parent;
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ChannelInfo channelInfo) {

    }
}
