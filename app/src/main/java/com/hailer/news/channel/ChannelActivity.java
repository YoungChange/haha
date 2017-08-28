package com.hailer.news.channel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.hailer.news.R;
import com.hailer.news.common.ActivityCode;
import com.hailer.news.common.BaseActivity;
import com.hailer.news.util.annotation.ActivityFragmentInject;

import java.util.ArrayList;

/**
 * Created by moma on 17-8-24.
 */

@ActivityFragmentInject(contentViewId = R.layout.activity_channel_manager,
        toolbarId = R.id.back_toolbar,
        toolbarBackImageButtonId = R.id.back_imagebutton,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.my_channel
)
public class ChannelActivity extends BaseActivity implements ChannelContract.View{

    private Context mContext;
    private BaseActivity mActivity;
    private RecyclerView mChannelManagerRv;
    private ChannelAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        mChannelManagerRv = (RecyclerView) findViewById(R.id.change_channel_rv);

    }

    private void initView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        mChannelManagerRv.setLayoutManager(layoutManager);


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
}
