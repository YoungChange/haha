package com.hailer.news.comments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.hailer.news.common.ActivityCode;
import com.hailer.news.common.BaseRecycleViewDivider;
import com.hailer.news.common.BaseActivity;
import com.hailer.news.common.BaseRecyclerViewHolder;
import com.hailer.news.common.CommentBar;
import com.hailer.news.login.LoginActivity;
import com.hailer.news.newsdetail.NewsDetailActivity;
import com.hailer.news.util.FuncUtil;
import com.hailer.news.util.InputMethodLayout;
import com.hailer.news.util.MeasureUtil;
import com.hailer.news.util.NetworkUtil;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.socks.library.KLog;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@ActivityFragmentInject(contentViewId = R.layout.activity_news_comment_list,
        toolbarId = R.id.back_toolbar,
        toolbarBackImageButtonId = R.id.back_imagebutton,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.news_comment
)
public class CommentsActivity extends BaseActivity implements CommentsContract.View{

    private Context mContext;
    private BaseActivity activity;


    private String mPostId;
    private int mCommentCount;
    private String mPostUrl;
    private String mPostTitle;



    private CommentsListAdapter newsCommentListAdapter;
    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Boolean mLoading = false;
    private CommentsPresenter mCommentsPresenter;
    private int mViewHolderPosition;

    private BaseRecyclerViewHolder mViewHolder;

    private CommentBar mCommentBar;
    private InputMethodLayout layout;

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        activity = this;

        mPostId = getIntent().getStringExtra("postId");
        mCommentCount = getIntent().getIntExtra("commentCount",0);
        mPostUrl = getIntent().getStringExtra("postUrl");
        mPostTitle = getIntent().getStringExtra("postTitle");
        KLog.e("-------CommentsListActivity------mPostId:"+mPostId+
                ";------mCommentCount:"+mCommentCount+
                ";------mPostUrl:"+mPostUrl+
                ";------mPostTitle:"+mPostTitle);

        mRecyclerView = (RecyclerView) findViewById(R.id.newscommentlist_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.comment_refresh_layout);

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
                        mCommentsPresenter.postComment(mPostId,comment);
                    }
                }else{
                    toast(getString(R.string.net_error));
                }
            }

            @Override
            public void gotoCommentListClick() {
                KLog.e("------CommentsActivity------- onclick , to comment list..............");
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

        mCommentsPresenter = new CommentsPresenter(this);
        mCommentsPresenter.getCommentsList(mPostId);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_imagebutton:
                Intent intent = new Intent();
                intent.putExtra("count",mCommentCount);
                setResult(ActivityCode.Result.NEWS_COMMENT_RESULT_CODE, intent);
                this.finish();
                break;
            default:
                toast(this.getString(R.string.unknow_error));
        }
    }

    @Override
    public void showCommentsList(List<CommentInfo> data,boolean isRefresh){
        KLog.e("-----List<CommentInfo>------:"+data.size());
        mLoading = false;
        KLog.e("showCommentsList...");
        if (newsCommentListAdapter == null) {
            initCommentList(data);
        }

        if(isRefresh){
            newsCommentListAdapter.setmData(data);
        }else{
            if (data == null || data.size() == 0) {
                toast(this.getString(R.string.all_loaded));
                return;
            }
            newsCommentListAdapter.addMoreData(data);
        }
    }

    @Override
    public void showErrorMsg(int error){

    }

    @Override
    public void resetVote() {
//        (CommentsListViewHolder)mViewHolder.
        newsCommentListAdapter.resetVote(mViewHolderPosition);
        ((CommentsListViewHolder) mViewHolder).addOneAnim();
    }

    private void initCommentList(final List<CommentInfo> newsCommentList) {

        //刷新监听事件
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCommentsPresenter.refreshData();
                mSwipeRefreshLayout.setRefreshing(false);
                newsCommentListAdapter.notifyDataSetChanged();
            }
        });

        newsCommentListAdapter = new CommentsListAdapter(this, newsCommentList);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // 给RecyclerView增加滑动监听
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                //mLoading 防止多次加载同一批数据
                if (!mLoading && totalItemCount < (lastVisibleItem + 2)) {
                    mLoading = true;
                    mCommentsPresenter.loadMoreData();
                }
            }
        });

        //添加分割线
        mRecyclerView.addItemDecoration(
                new BaseRecycleViewDivider(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        MeasureUtil.dip2px(this,1),
                        getResources().getColor(R.color.divide_newslist)));

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(250);
        mRecyclerView.getItemAnimator().setMoveDuration(250);
        mRecyclerView.getItemAnimator().setChangeDuration(250);
        mRecyclerView.getItemAnimator().setRemoveDuration(250);

        mRecyclerView.setAdapter(newsCommentListAdapter);
    }

    public void vote(CommentInfo commentInfo,BaseRecyclerViewHolder viewHolder) {
        mCommentsPresenter.voteComment(commentInfo);
        mViewHolder = viewHolder;
        mViewHolderPosition = viewHolder.getAdapterPosition();
    }

    public void unVote(int id) {
        mCommentsPresenter.unVoteComment(id);
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

        mCommentsPresenter.refreshData();
        newsCommentListAdapter.notifyDataSetChanged();

        mCommentBar.setEditView("");

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KLog.e("viewFocused:"+getCurrentFocus().getClass().getName());
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            View viewFocused = getCurrentFocus();
            View v = mCommentBar;
            KLog.e("-------NewsDetailActivity--dispatchTouchEvent---view:"+v.getClass().getName());
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
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, mPostUrl);
                        sendIntent.setType("text/plain");
                        sendIntent.setPackage("com.whatsapp");
                        startActivity(sendIntent);

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

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent();
//        intent.putExtra("count",mCommentCount);
//        setResult(ActivityCode.Result.NEWS_COMMENT_RESULT_CODE, intent);
//        this.finish();
//    }
}
