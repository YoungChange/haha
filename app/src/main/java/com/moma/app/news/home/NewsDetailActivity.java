package com.moma.app.news.home;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.moma.app.news.R;
import com.moma.app.news.api.bean.NewsDetail;
import com.moma.app.news.base.BaseActivity;
import com.moma.app.news.home.presenter.INewsDetailPresenter;
import com.moma.app.news.home.presenter.INewsDetailPresenterImpl;
import com.moma.app.news.home.view.INewsDetailView;
import com.moma.app.news.util.GlideUtils;
import com.moma.app.news.util.annotation.ActivityFragmentInject;

//import zhou.widget.RichText;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichText;
/**
 * Created by moma on 17-7-17.
 */

@ActivityFragmentInject(contentViewId = R.layout.activity_news_detail,
        toolbarId = R.id.back_toolbar,
        toolbarBackImageButtonId = R.id.back_imagebutton
        )
public class NewsDetailActivity extends BaseActivity<INewsDetailPresenter> implements INewsDetailView {

    private TextView mDetailTitle;
    private TextView mDetailTime;
    //private RichText mDetailBody;
    private TextView mDetailBody;
    private ImageView mDetailImage;


    @Override
    protected void initView() {

        mDetailTitle = (TextView) findViewById(R.id.news_detail_title);
        mDetailTime = (TextView) findViewById(R.id.news_detail_time);
        mDetailBody = (TextView) findViewById(R.id.news_detail_body);
        mDetailImage = (ImageView) findViewById(R.id.news_detail_image);

        //mDetailBody.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        String mNewsListPostId = getIntent().getStringExtra("postid");
        String mNewsListImgSrc = getIntent().getStringExtra("imgsrc");

        //GlideUtils.loadDefault(mNewsListImgSrc, mDetailImage, null, null, DiskCacheStrategy.RESULT);

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
                toast("不知道你点击了啥");
        }
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }



}
