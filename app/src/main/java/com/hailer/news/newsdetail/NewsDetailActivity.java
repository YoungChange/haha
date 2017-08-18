package com.hailer.news.newsdetail;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
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
import com.hailer.news.util.TextUtil;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.socks.library.KLog;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.zzhoujay.richtext.RichText;

import java.net.MalformedURLException;
import java.net.URL;

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
    private Button mShareButton;

    private String mPostId;

    NewsDetailPresenter mDetailPresenter;

    private InputMethodLayout layout;

    RelativeLayout normalLayout;
    LinearLayout netErrorLayout;

    private PopupWindow popupWindow;
    private int navigationHeight;

    private Context mContext;

    private int TWEET_COMPOSER_REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

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
        mShareButton = (Button) findViewById(R.id.share_btn);
        mShareButton.setOnClickListener(this);

        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        navigationHeight = getResources().getDimensionPixelSize(resourceId);
        initShareDlg();

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
        KLog.e("----rebackfromFacebook---requestCode:"+requestCode+"--resultCode:"+resultCode);
        if(requestCode==1){
            if(UserManager.getInstance().getServerToken()!=null && !UserManager.getInstance().getServerToken().isEmpty()){
                mSendCommentButton.callOnClick();
            }
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
                comment = TextUtil.noCRLF(comment);

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
            case R.id.share_btn:
                //设置位置
                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, navigationHeight);
                setBackgroundAlpha(0.5f);
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




    public Uri getHeaderIconUri(){   //要上传的图片
        Resources r =  getApplicationContext().getResources();
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.drawable.curry) + "/"
                + r.getResourceTypeName(R.drawable.curry) + "/"
                + r.getResourceEntryName(R.drawable.curry));
    }



    void initShareDlg(){

        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }

//        CallbackManager callbackManager;
//        final ShareDialog shareDialog;
//        callbackManager = CallbackManager.Factory.create();
//        shareDialog = new ShareDialog(this);
//        // this part is optional
//        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
//            @Override
//            public void onSuccess(Sharer.Result result) {
//                KLog.e("--Facebook--shareDialog---onSuccess-");
//            }
//
//            @Override
//            public void onCancel() {
//                KLog.e("--Facebook--shareDialog---onCancel-");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                KLog.e("--Facebook--shareDialog---onError-[error]:"+error.toString());
//            }
//        });




        View view = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);

        ImageButton mDialogFacebookBtn = view.findViewById(R.id.dialog_facebook_btn);
        ImageButton mDialogTwitterBtn = view.findViewById(R.id.dialog_twitter_btn);
        ImageButton mDialogMessageBtn = view.findViewById(R.id.dialog_message_btn);
        ImageButton mDialogWhatsAppBtn = view.findViewById(R.id.dialog_whatsapp_btn);
        Button mDialogCancelBtn = view.findViewById(R.id.dialog_cancel_btn);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.dialog_facebook_btn:
                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse("https://hailer.news/news/the-cheapest-joint-venture-suv-fuel-consumption-of-3-cents-than-the-toyota-province-the-value-of-to"))
                                .build();
                        if (ShareDialog.canShow(ShareLinkContent.class)) {
                            ShareDialog.show((Activity)mContext,content);
                        }
                        break;
                    case R.id.dialog_twitter_btn:
                        try {
                            TweetComposer.Builder builder = null;
                            Intent intentTwitter;
                            intentTwitter = new TweetComposer.Builder(mContext)
                                    .text("测试222222")
                                    .url(new URL("https://hailer.news/news/the-cheapest-joint-venture-suv-fuel-consumption-of-3-cents-than-the-toyota-province-the-value-of-to"))
                                    .createIntent();
                            startActivityForResult(intentTwitter, TWEET_COMPOSER_REQUEST_CODE);
                        } catch (MalformedURLException e) {
                            KLog.e("--twitter_share_error----MalformedURLException-:"+e);
                            e.printStackTrace();
                        }

                        break;
                    case R.id.dialog_message_btn:



                        break;
                    case R.id.dialog_whatsapp_btn:
                        break;
                    case R.id.dialog_cancel_btn:
                        popupWindow.dismiss();
                        break;
                }

            }
        };
        mDialogFacebookBtn.setOnClickListener(listener);
        mDialogTwitterBtn.setOnClickListener(listener);
        mDialogMessageBtn.setOnClickListener(listener);
        mDialogWhatsAppBtn.setOnClickListener(listener);
        mDialogCancelBtn.setOnClickListener(listener);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.setOutsideTouchable(true);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1);
            }
        });
        popupWindow.setAnimationStyle(R.style.PopupWindow);
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

}
