package com.hailer.news.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.Toast;

import com.hailer.news.R;
import com.hailer.news.UserManager;
import com.hailer.news.util.InputMethodLayout;
import com.hailer.news.util.NetworkUtil;
import com.hailer.news.util.TextUtil;
import com.socks.library.KLog;

/**
 * Created by moma on 17-8-22.
 */

public class CommentBar extends LinearLayout implements View.OnClickListener{

    private Context mContext;

    private LinearLayout sendCommentBar;
    private LinearLayout gotoCommentListBar;

    private EditText mSendCommentEditText;
    private Button mSendCommentButton;

    private Button mGotoCommentListButton;
    private EditText mEditviewButton;
    private TextView mCommentCountTextView;
    private Button mShareButton;

    private PopupWindow popupWindow;

    private String mPostTitle="Title";
    private String mPostUrl="http://developer.android.com/";

    private CommentBarClickListener mListener;

    private InputMethodLayout layout;

    public CommentBar setLayout(InputMethodLayout layout) {
        this.layout = layout;
        this.layout.setOnkeyboarddStateListener(new InputMethodLayout.onKeyboardsChangeListener() {

            @Override
            public void onKeyBoardStateChange(int state) {
                // TODO Auto-generated method stub
                switch (state) {
                    case InputMethodLayout.KEYBOARD_STATE_SHOW:
                        KLog.e("------------- onKeyBoardStateChange , isSoftInputOpen = true; ..............");
                        sendCommentBar.setVisibility(View.VISIBLE);
                        gotoCommentListBar.setVisibility(View.GONE);

                        mSendCommentEditText.requestFocus();
                        break;
                    case InputMethodLayout.KEYBOARD_STATE_HIDE:
                        KLog.e("------------- onKeyBoardStateChange , isSoftInputOpen = false; ..............");
                        sendCommentBar.setVisibility(View.GONE);
                        gotoCommentListBar.setVisibility(View.VISIBLE);

                        break;
                }
            }
        });
        return this;
    }

    public CommentBar setCount(int count){
        mCommentCountTextView.setText(String.valueOf(count));
        return this;
    }

    public CommentBar setEditView(String string){
        mSendCommentEditText.setText(string);
        return this;
    }

    public String getEditViewText(){
        return mSendCommentEditText.getText().toString();
    }

    public CommentBar(Context context) {
        this(context,null);
    }

    public CommentBar(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        mContext=context;
        initView(context);
    }

    public void initView(Context context){

        LayoutInflater.from(context).inflate(R.layout.bar_comment, this, true);

        sendCommentBar = findViewById(R.id.send_comment_layout);
        gotoCommentListBar = findViewById(R.id.goto_comment_list_layout);

        mSendCommentButton = findViewById(R.id.send_comment_button);
        mSendCommentButton.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                KLog.e("-------------onclick , send comment ..............");
                String comment = mSendCommentEditText.getText().toString();
                comment = TextUtil.noCRLF(comment);
                mListener.sendCommentClick(comment);
            }
        });
        mSendCommentEditText = findViewById(R.id.comment_edittext);
        mSendCommentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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

        mGotoCommentListButton = findViewById(R.id.goto_comment_list_button);
        mGotoCommentListButton.setOnClickListener(this);
        mEditviewButton = findViewById(R.id.editview_button);
        mEditviewButton.setOnClickListener(this);
        mCommentCountTextView = findViewById(R.id.comment_count_textview);

        mShareButton = findViewById(R.id.share_btn);
        mShareButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(mListener == null){
            return;
        }
        switch(v.getId()){
            case R.id.send_comment_button:
                KLog.e("-------------onclick , send comment ..............");
                String comment = mSendCommentEditText.getText().toString();
                comment = TextUtil.noCRLF(comment);
                mListener.sendCommentClick(comment);
                break;
            case R.id.goto_comment_list_button:
                mListener.gotoCommentListClick();
                break;
            case R.id.editview_button:
                mListener.editviewClick();
                break;
            case R.id.share_btn:
                mListener.shareClick(v);
                break;
            default:
                mListener.defaultClick();
        }
    }


    public void callSendCommentClick(){
        mSendCommentButton.callOnClick();
    }

    /**
     * 设置监听器
     * @param commentBarClickListener
     */
    public void setOnCommentBarClickListener(CommentBarClickListener commentBarClickListener){
        this.mListener = commentBarClickListener;
    }

    /**
     * 暴露对外的接口
     */
    public interface CommentBarClickListener{
        void sendCommentClick(String comment);
        void gotoCommentListClick();
        void editviewClick();
        void shareClick(View v);
        void defaultClick();
    }

}
