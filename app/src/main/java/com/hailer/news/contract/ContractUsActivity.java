package com.hailer.news.contract;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hailer.news.R;
import com.hailer.news.contract.presenter.IContractUsPresenter;
import com.hailer.news.contract.presenter.IContractUsPresenterImpl;
import com.hailer.news.contract.view.IContractUsView;
import com.hailer.news.util.annotation.ActivityFragmentInject;
import com.hailer.news.util.bean.FeedBackMessage;

/**
 * Created by moma on 17-7-24.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_contract,
        handleRefreshLayout = true,
        toolbarId = R.id.back_toolbar,
        toolbarBackImageButtonId = R.id.back_imagebutton,
        toolbarTextViewId = R.id.toolbar_title,
        toolbarTextViewTitle = R.string.contract_us
)
public class ContractUsActivity extends BaseActivity<IContractUsPresenter> implements IContractUsView {

    private FeedBackMessage message;
    private EditText emailEditview;
    private EditText feedbackEditview;
    private Button sendButton;
    private InputMethodManager manager;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onCreate(savedInstanceState);
        manager  = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

    }


    @Override
    protected void initView() {
        emailEditview  = (EditText) findViewById(R.id.email_editview);
        feedbackEditview = (EditText) findViewById(R.id.feedback_editview);
        sendButton = (Button) findViewById(R.id.send_button);
        message = new FeedBackMessage(emailEditview.getText().toString(),feedbackEditview.getText().toString());
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_imagebutton:
                this.finish();
                break;
            case R.id.send_button:
                message.setUserEmail(emailEditview.getText().toString());
                message.setMessageContent(feedbackEditview.getText().toString());
                mPresenter = new IContractUsPresenterImpl(this, message);
                showPopUp();
                break;
            case R.id.diagle_button:
                if(alertDialog != null){
                    alertDialog.dismiss();
                }
                this.finish();
                break;
            default:
                toast(this.getString(R.string.unknow_error));
        }
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
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 弹出弹框的方法
     */
    private void showPopUp(){
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog);
        TextView tv_title = (TextView) window.findViewById(R.id.diagle_textview);
        Button button = (Button) window.findViewById(R.id.diagle_button);
        button.setOnClickListener(this);
    }




}
