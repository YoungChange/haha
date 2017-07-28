package com.hailer.news.home;


import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hailer.news.R;
import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.base.BaseActivity;
import com.hailer.news.home.presenter.INewsDetailPresenter;
import com.hailer.news.home.presenter.INewsDetailPresenterImpl;
import com.hailer.news.home.view.INewsDetailView;
import com.hailer.news.util.annotation.ActivityFragmentInject;

import com.zzhoujay.richtext.RichText;
/**
 * Created by moma on 17-7-17.
 */

@ActivityFragmentInject(contentViewId = R.layout.activity_news_detail,
        toolbarId = R.id.back_toolbar,
        toolbarBackImageButtonId = R.id.back_imagebutton,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.news_detail
        )
public class NewsDetailActivity extends BaseActivity<INewsDetailPresenter> implements INewsDetailView {

    private TextView mDetailTitle;
    private TextView mDetailTime;
    private TextView mDetailBody;
    private ImageView mDetailImage;


    @Override
    protected void initView() {

        mDetailTitle = (TextView) findViewById(R.id.news_detail_title);
        mDetailTime = (TextView) findViewById(R.id.news_detail_time);
        mDetailBody = (TextView) findViewById(R.id.news_detail_body);
        mDetailImage = (ImageView) findViewById(R.id.news_detail_image);

        String mNewsListPostId = getIntent().getStringExtra("postid");
        String mNewsListImgSrc = getIntent().getStringExtra("imgsrc");

        mPresenter = new INewsDetailPresenterImpl(this,mNewsListPostId);

    }

    @Override
    public void initNewsDetail(final NewsDetail data) {

        mDetailTitle.setText(data.title);
        mDetailTime.setText(data.date);

        if (!TextUtils.isEmpty(data.content)) {
            //mDetailBody.setRichText(data.content);
            RichText.fromHtml(data.content)
                    //.autoPlay(true)
                    .into(mDetailBody);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_imagebutton:
                this.finish();
                break;
            default:
                toast(this.getString(R.string.what_you_did));
        }
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }



}
