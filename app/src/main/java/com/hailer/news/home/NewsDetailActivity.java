package com.hailer.news.home;


import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hailer.news.R;
import com.hailer.news.UserManager;
import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.base.BaseActivity;
import com.hailer.news.home.presenter.INewsDetailPresenter;
import com.hailer.news.home.presenter.INewsDetailPresenterImpl;
import com.hailer.news.home.presenter.ISendCommentPresenter;
import com.hailer.news.home.presenter.ISendCommentPresenterImpl;
import com.hailer.news.home.view.INewsDetailView;
import com.hailer.news.util.annotation.ActivityFragmentInject;

import com.socks.library.KLog;
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
public class NewsDetailActivity extends BaseActivity<INewsDetailPresenter> implements INewsDetailView , View.OnFocusChangeListener{

    private TextView mDetailTitle;
    private TextView mDetailTime;
    private TextView mDetailBody;
    private ImageView mDetailImage;


    private EditText mCommentEditText;
    private ImageButton mCommentButton;
    private View rootView;

    LinearLayout  commentButtonLinearLayout;
    LayoutInflater inflater;

    String mPostId;

    ISendCommentPresenter mSendCommentPresenter;

    @Override
    protected void initView() {

        mDetailTitle = (TextView) findViewById(R.id.news_detail_title);
        mDetailTime = (TextView) findViewById(R.id.news_detail_time);
        mDetailBody = (TextView) findViewById(R.id.news_detail_body);
        mDetailImage = (ImageView) findViewById(R.id.news_detail_image);

        commentButtonLinearLayout= (LinearLayout) findViewById(R.id.commentbutton_layout);
        inflater = LayoutInflater.from(this);

        mCommentButton = (ImageButton) findViewById(R.id.jumptocommentlist_button);
        mCommentButton.setOnClickListener(this);


        mCommentEditText = (EditText) findViewById(R.id.comment_edittext);
        mCommentEditText.setOnFocusChangeListener(this);

        String mNewsDetailPostId = getIntent().getStringExtra("postid");
        mPostId = mNewsDetailPostId;

        mPresenter = new INewsDetailPresenterImpl(this,mNewsDetailPostId);
    }

    @Override
    public void initNewsDetail(final NewsDetail data) {

        mDetailTitle.setText(data.getTitle());
        mDetailTime.setText(data.getDate());

        String content = data.getContent();
        if (!TextUtils.isEmpty(content)) {
            //mDetailBody.setRichText(data.content);
            RichText.fromHtml(content)
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
            case R.id.jumptocommentlist_button:
                String comment = mCommentEditText.getText().toString();
                String token = UserManager.getInstance().getServerToken();
                KLog.e("bailei, comment="+comment+", token="+token);
                if (comment != null && !comment.isEmpty()
                        && token != null && !token.isEmpty()) {
                    mSendCommentPresenter = new ISendCommentPresenterImpl(this, mPostId, token, comment);
                } else {
                    Intent intent = new Intent(this, CommentsListActivity.class);
                    intent.putExtra("postId", mPostId);
                    startActivity(intent);
                }
                break;
            case R.id.sendcomment_button:
                toast("发送comment！");
                /*
                String comment = mCommentEditText.getText().toString();
                String token = UserManager.getInstance().getServerToken();
                if (comment != null && token != null)
                mSendCommentPresenter = new ISendCommentPresenterImpl(this, mPostId, token, comment);
                */
//                mPresenter = new ISendCommentPresenterImpl(this, "评论内容");
                break;
            default:
                toast(this.getString(R.string.what_you_did));
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        KLog.e("--------------bailei.........onFocusChange, hasFocus="+hasFocus+", id="+v.getId());
        switch(v.getId()){
            case R.id.comment_edittext:
                KLog.e("--------------bailei.........onFocusChange, edittext");
                if(hasFocus==true){
//                    toast("获得焦点");
                    View view = inflater.inflate(R.layout.button_sendcomment, null);
                    //LinearLayout sendCommentLinearLayout = (LinearLayout) view.findViewById(R.id.sendcomment_layout);
                    //commentButtonLinearLayout.removeAllViews();
                    //commentButtonLinearLayout.addView(sendCommentLinearLayout);
                    //mCommentButton = (ImageButton) findViewById(R.id.sendcomment_button);
                    mCommentButton.setOnClickListener(this);
                    break;
                }else{
//                    toast("失去焦点");
//                    View view = inflater.inflate(R.layout.button_jumptocommentlist, null);
//                    LinearLayout jumpToCommentListLinearLayout = (LinearLayout) view.findViewById(R.id.jumptocommentlist_layout);
//                    commentButtonLinearLayout.removeAllViews();
//                    commentButtonLinearLayout.addView(jumpToCommentListLinearLayout);
                    //mCommentButton = (ImageButton) findViewById(R.id.jumptocommentlist_button);
                    mCommentButton.setOnClickListener(this);
                    break;
                }
            default:
                toast(getString(R.string.what_you_did));
                break;
        }
    }


    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * 隐藏软件盘方法
     */
    private void hideSoftInput(IBinder token) {

        mCommentEditText.clearFocus();

        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
