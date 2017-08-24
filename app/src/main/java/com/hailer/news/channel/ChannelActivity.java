package com.hailer.news.channel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hailer.news.R;
import com.hailer.news.common.ActivityCode;
import com.hailer.news.common.BaseActivity;
import com.hailer.news.util.annotation.ActivityFragmentInject;

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
    private BaseActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        activity = this;
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
}
