package com.hailer.news.newsdetailandcomment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
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
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;
import com.hailer.news.NewsApplication;
import com.hailer.news.R;
import com.hailer.news.UserManager;
import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.common.ActivityCode;
import com.hailer.news.common.BaseActivity;
import com.hailer.news.common.BaseRecyclerViewHolder;
import com.hailer.news.common.CommentBar;
import com.hailer.news.login.LoginActivity;
import com.hailer.news.news.NewsContract;
import com.hailer.news.util.FuncUtil;
import com.hailer.news.util.InputMethodLayout;
import com.hailer.news.util.InputMethodLayout.onKeyboardsChangeListener;
import com.hailer.news.util.MeasureUtil;
import com.hailer.news.util.NetworkUtil;
import com.hailer.news.util.TextUtil;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.socks.library.KLog;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.zzhoujay.richtext.RichText;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.BreakIterator;
import java.util.List;

/**
 * Created by moma on 17-7-17.
 */

@ActivityFragmentInject(contentViewId = R.layout.activitymodified,
        toolbarId = R.id.back_toolbar,
        toolbarBackImageButtonId = R.id.back_imagebutton,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.news_detail
        )
public class NewsDetailAddCommentActivity extends BaseActivity implements NewsDetailAddCommentContract.View {

    private Context mContext;
    private BaseActivity activity;

    private NewsDetailFragment mNewsDetailFragment;
    private NewsCommentFragment mNewsCommentFragment;
    private NewsDetailAddCommentContract.Presenter mNewsDetailAddCommentPresenter;

    private String mPostId;
    private int mCommentCount;
    private String mPostUrl;


    private String mPostTitle;


    private InputMethodLayout layout;
    private CommentBar mCommentBar;
    private PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        activity = this;

        //!!!!给石磊要URL
        mPostId = getIntent().getStringExtra("postId");
        mPostTitle = getIntent().getStringExtra("postTitle");
        mPostUrl = getIntent().getStringExtra("postUrl");

        mNewsDetailAddCommentPresenter = new NewsDetailAddCommentPresenter(this);
        mNewsDetailFragment = NewsDetailFragment.newInstance(mPostId);
        mNewsDetailFragment.setPresenter(mNewsDetailAddCommentPresenter);
        mNewsCommentFragment = NewsCommentFragment.newInstance(mPostId,mPostUrl,mPostTitle);
        mNewsCommentFragment.setPresenter(mNewsDetailAddCommentPresenter);

        changeFragment(mNewsDetailFragment);

        initShareDlg();

        mCommentBar = (CommentBar) findViewById(R.id.comment_bar);
        layout = (InputMethodLayout) findViewById(R.id.inputmethod_layout);
        mCommentBar.setLayout(layout).setCount(mCommentCount);
        mCommentBar.setOnCommentBarClickListener(new CommentBar.CommentBarClickListener() {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

            @Override
            public void sendCommentClick(String comment) {
                String token = UserManager.getInstance().getServerToken();
                KLog.e(" comment="+comment+", token="+token);

                if(NetworkUtil.isConnected(NewsApplication.getContext())){
                    if(comment == null || comment.isEmpty()){
                        KLog.e("--------------Comment is null");
                        toast(getString(R.string.comment_is_null));
                    }else{
                        KLog.e("--------------send Comment");
                        mNewsDetailAddCommentPresenter.postComment(mPostId,comment);
                    }
                }else{
                    toast(getString(R.string.net_error));
                }
            }

            @Override
            public void gotoCommentListClick() {

                if(mNewsCommentFragment.isVisible()){
                    KLog.e("------mNewsCommentFragment---is---- showing");
                    changeFragment(mNewsDetailFragment);
                    changeToolBar(R.string.news_detail);
                }else if(mNewsDetailFragment.isVisible() && mCommentCount != 0){
                    KLog.e("------mNewsCommentFragment-----is---- showing");
                    changeFragment(mNewsCommentFragment);
                    changeToolBar(R.string.news_comment);
                }

            }

            @Override
            public void editviewClick() {
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void shareClick(View v) {
                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, MeasureUtil.getNavigationBarHeight(activity));
                setBackgroundAlpha(0.5f);
            }

            @Override
            public void defaultClick() {
                activity.toast(activity.getString(R.string.unknow_error));
            }
        });
    }

    @Override
    public void showDetail(NewsDetail data){
        mCommentCount = data.getCommentsCount();
        mCommentBar.setCount(mCommentCount);
        mNewsDetailFragment.showDetail(data);
    }

    @Override
    public void handleError(){
        mNewsDetailFragment.handleError();
    }

    @Override
    public void showCommentsList(List<CommentInfo> data,boolean isRefresh){
        mNewsCommentFragment.showCommentsList(data,isRefresh);
    }

    @Override
    public void showErrorMsg(int error) {

    }

    @Override
    public void resetVote() {
        mNewsCommentFragment.resetVote();
    }

    @Override
    public void popLoginDlg() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent,ActivityCode.Request.NEWS_COMMENT_TO_LOGIN_REQUEST_CODE);
    }

    @Override
    public void showCommentMsg(){
        toast(getString(R.string.send_uccess));
        mCommentBar.setCount(++mCommentCount);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }

        mCommentBar.setEditView("");

    }

    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_layout,fragment);
        fragmentTransaction.commit();

    }

    private void changeToolBar(int resId){
        mToolBarTv.setText(resId);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        KLog.e("viewFocused:"+getCurrentFocus().getClass().getName());
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            View viewFocused = getCurrentFocus();
            View v = mCommentBar;
            KLog.e("-------NewsDetailAddCommentActivity--dispatchTouchEvent---view:"+v.getClass().getName());
            boolean b = MeasureUtil.isShouldHideInput(v, ev);
            KLog.e("----isShouldHideInput------b:"+b);
            if (viewFocused!=null && b) {
                IBinder token = viewFocused.getWindowToken();
                if (token != null) {
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    void initShareDlg(){

        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }

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
                        popupWindow.dismiss();
                        ShareLinkContent facebookContent = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(mPostUrl))
                                .build();
                        if (ShareDialog.canShow(ShareLinkContent.class)) {
                            ShareDialog.show((Activity)mContext,facebookContent);
                        }
                        break;
                    case R.id.dialog_twitter_btn:
                        popupWindow.dismiss();
                        try {
                            Intent intentTwitter;
                            intentTwitter = new TweetComposer.Builder(mContext)
                                    .text(mPostTitle)
                                    .url(new URL(mPostUrl))
                                    .createIntent();
                            startActivityForResult(intentTwitter, ActivityCode.Request.TO_TWEET_COMPOSER_REQUEST_CODE);
                        } catch (MalformedURLException e) {
                            KLog.e("--twitter_share_error----MalformedURLException-:"+e);
                            e.printStackTrace();
                        }

                        break;
                    case R.id.dialog_message_btn:
                        popupWindow.dismiss();
                        ShareLinkContent messagerContent = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(mPostUrl))
                                .build();
                        if (MessageDialog.canShow(ShareLinkContent.class)) {
                            MessageDialog.show((Activity)mContext, messagerContent);
                        }

                        break;
                    case R.id.dialog_whatsapp_btn:
                        popupWindow.dismiss();
                        if(FuncUtil.isAvilible(mContext,"com.whatsapp")){
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, mPostUrl);
                            sendIntent.setType("text/plain");
                            sendIntent.setPackage("com.whatsapp");
                            startActivity(sendIntent);
                        }else{
                            toast(getString(R.string.no_app));
                        }

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
    }

    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_imagebutton:
                if(mNewsCommentFragment.isVisible()){
                    changeFragment(mNewsDetailFragment);
                    changeToolBar(R.string.news_detail);
                }else if(mNewsDetailFragment.isVisible()){
                    this.finish();
                }
                break;
            default:
                toast(this.getString(R.string.unknow_error));
        }
    }

    public void vote(CommentInfo commentInfo,BaseRecyclerViewHolder viewHolder) {
        mNewsCommentFragment.vote(commentInfo,viewHolder);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        KLog.e("----rebackfromFacebook---requestCode:"+requestCode+"--resultCode:"+resultCode);
        if(requestCode==ActivityCode.Request.NEWS_DETAIL_TO_LOGIN_REQUEST_CODE){
            if(UserManager.getInstance().getServerToken()!=null && !UserManager.getInstance().getServerToken().isEmpty()){
                mCommentBar.callSendCommentClick();
            }
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm.isActive()){
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

        if(popupWindow.isShowing()){
            popupWindow.dismiss();
        }

    }
}
