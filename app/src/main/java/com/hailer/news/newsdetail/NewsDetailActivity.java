package com.hailer.news.newsdetail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hailer.news.NewsApplication;
import com.hailer.news.R;
import com.hailer.news.UserManager;
import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.comments.CommentsActivity;
import com.hailer.news.login.LoginActivity;
import com.hailer.news.common.BaseActivity;
import com.hailer.news.util.InputMethodLayout;
import com.hailer.news.util.InputMethodLayout.onKeyboardsChangeListener;
import com.hailer.news.util.NetworkUtil;
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
public class NewsDetailActivity extends BaseActivity implements NewsDetailContract.View {

    private int mCommentsCount = 0;

    private TextView mDetailTitle;
    private TextView mDetailTime;
    private TextView mDetailBody;

    private LinearLayout sendCommentBar;
    private LinearLayout gotoCommentListBar;


    private EditText mSendCommentEditText;
    private Button mSendCommentButton;

    private Button mGotoCommentListButton;
    private EditText mEditviewButton;
    private TextView mCommentCountTextView;

    private String mPostId;

    NewsDetailPresenter mDetailPresenter;

    private InputMethodLayout layout;

    RelativeLayout normalLayout;
    LinearLayout netErrorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        normalLayout = (RelativeLayout) findViewById(R.id.normal_layout);
        netErrorLayout = (LinearLayout) findViewById(R.id.net_error_layout);

        sendCommentBar = (LinearLayout) findViewById(R.id.send_comment_layout);
        gotoCommentListBar = (LinearLayout) findViewById(R.id.goto_comment_list_layout);

        mDetailTitle = (TextView) findViewById(R.id.news_detail_title);
        mDetailTime = (TextView) findViewById(R.id.news_detail_time);
        mDetailBody = (TextView) findViewById(R.id.news_detail_body);

        mSendCommentButton = (Button) findViewById(R.id.send_comment_button);
        mSendCommentButton.setOnClickListener(this);
        mSendCommentEditText = (EditText) findViewById(R.id.comment_edittext);
        mSendCommentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                KLog.e("-------mSendCommentEditText------onTextChanged:"+s.toString());

                if(s.length()==0){
                    mSendCommentButton.setTextColor(getResources().getColor(R.color.colorTextIsNull));
                }else{
                    mSendCommentButton.setTextColor(getResources().getColor(R.color.colorTextNotNull));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mGotoCommentListButton = (Button) findViewById(R.id.goto_comment_list_button);
        mEditviewButton = (EditText) findViewById(R.id.editview_button);
        mCommentCountTextView = (TextView) findViewById(R.id.comment_count_textview);
        mGotoCommentListButton.setOnClickListener(this);
        mEditviewButton.setOnClickListener(this);



        Button retryButton = (Button) findViewById(R.id.retry_button);
        retryButton.setOnClickListener(this);


        String mNewsDetailPostId = getIntent().getStringExtra("postid");
        mPostId = mNewsDetailPostId;

        layout = (InputMethodLayout) findViewById(R.id.activity_newsdetail_rootview);
        layout.setOnkeyboarddStateListener(new onKeyboardsChangeListener() {// 监听软键盘状态

            @Override
            public void onKeyBoardStateChange(int state) {
                // TODO Auto-generated method stub
                switch (state) {
                    case InputMethodLayout.KEYBOARD_STATE_SHOW:
                        KLog.e("------------- onKeyBoardStateChange , isSoftInputOpen = true; ..............");
                        sendCommentBar.setVisibility(View.VISIBLE);
                        gotoCommentListBar.setVisibility(View.GONE);
                        break;
                    case InputMethodLayout.KEYBOARD_STATE_HIDE:
                        KLog.e("------------- onKeyBoardStateChange , isSoftInputOpen = false; ..............");
                        sendCommentBar.setVisibility(View.GONE);
                        gotoCommentListBar.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        mDetailPresenter = new NewsDetailPresenter(this);
        mDetailPresenter.getDetail(mPostId);

    }

    @Override
    public void showDetail(NewsDetail data){

        normalLayout.setVisibility(View.VISIBLE);
        netErrorLayout.setVisibility(View.GONE);

        mDetailTitle.setText(data.getTitle());
        mDetailTime.setText(data.getDate());
        mCommentsCount = data.getCommentsCount();

        String content = data.getContent();
        if (!TextUtils.isEmpty(content)) {
            RichText.fromHtml(content)
                    //.autoPlay(true)
                    .into(mDetailBody);
        }

        mCommentCountTextView.setText(""+mCommentsCount);
    }

    @Override
    public void showCommentMsg(){
        toast(getString(R.string.send_uccess));
        mCommentCountTextView.setText(""+(++mCommentsCount));
        mSendCommentEditText.setText("");

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void handleError(){
        normalLayout.setVisibility(View.GONE);
        netErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void popLoginDlg(){
        Intent intent = new Intent(NewsDetailActivity.this, LoginActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(UserManager.getInstance().getServerToken()!=null && !UserManager.getInstance().getServerToken().isEmpty()){
            mSendCommentButton.callOnClick();
        }
    }


    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        switch(v.getId()){
            case R.id.back_imagebutton:
                this.finish();
                break;

            case R.id.send_comment_button:
                KLog.e("-------------onclick , send comment ..............");
                String comment = mSendCommentEditText.getText().toString();
                String token = UserManager.getInstance().getServerToken();
                KLog.e(" comment="+comment+", token="+token);

                if(NetworkUtil.isConnected(NewsApplication.getContext())){
                    if(comment == null || comment.isEmpty()){
                        KLog.e("--------------Comment is null");
                        toast(getString(R.string.comment_is_null));
                    }else{
                        KLog.e("--------------send Comment");
                        mDetailPresenter.postComment(mPostId,comment);
                    }
                }else{
                    toast(getString(R.string.net_error));
                }


                break;
            case R.id.goto_comment_list_button:
                KLog.e("------------- onclick , to comment list..............");
                //有评论的时候跳转到评论列表
                if (mCommentsCount != 0) {
                    Intent intent = new Intent(this, CommentsActivity.class);
                    intent.putExtra("postId", mPostId);
                    intent.putExtra("commentCount",mCommentsCount);
                    startActivity(intent);
                }
                break;
            case R.id.editview_button:
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.retry_button:
                mDetailPresenter = new NewsDetailPresenter(this);
                mDetailPresenter.getDetail(mPostId);
                break;
            default:
                toast(this.getString(R.string.unknow_error));
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            View viewFocused = getCurrentFocus();
            View v = sendCommentBar;
            KLog.e("-------NewsDetailActivity--dispatchTouchEvent---view:"+v.getClass().getName());

            if (viewFocused!=null && isShouldHideInput(v, ev)) {
                hideSoftInput(viewFocused.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null ) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            KLog.e("-----View v[left:"+left+";top:"+top+";bottom:"+bottom+";right:"+right+"]");
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
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
