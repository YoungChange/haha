package com.moma.app.news.contract;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.moma.app.news.R;
import com.moma.app.news.base.BaseActivity;
import com.moma.app.news.contract.presenter.IContractUsPresenter;
import com.moma.app.news.contract.presenter.IContractUsPresenterImpl;
import com.moma.app.news.contract.view.IContractUsView;
import com.moma.app.news.util.annotation.ActivityFragmentInject;
import com.moma.app.news.util.bean.FeedBackMessage;

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
public class ContractUsActivity extends BaseActivity<IContractUsPresenter> implements IContractUsView{

    private FeedBackMessage message;
    private EditText emailEditview;
    private EditText feedbackEditview;
    private Button sendButton;
    private InputMethodManager manager;

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
//                mPresenter = new IContractUsPresenterImpl(this, message);
                break;
            default:
                toast("不知道你点击了啥");
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        // TODO Auto-generated method stub
//        if(event.getAction() == MotionEvent.ACTION_DOWN){
//            if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
//                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }
//        return super.onTouchEvent(event);
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
