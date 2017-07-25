package com.moma.app.news.contract;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
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
        toolbarBackImageButtonId = R.id.back_imagebutton
)
public class ContractUsActivity extends BaseActivity<IContractUsPresenter> implements IContractUsView{

    private FeedBackMessage message;
    private EditText emailEditview;
    private EditText feedbackEditview;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onCreate(savedInstanceState);

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

}
