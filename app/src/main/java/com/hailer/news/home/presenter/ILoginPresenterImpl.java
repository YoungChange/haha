package com.hailer.news.home.presenter;

import com.hailer.news.base.presenter.BasePresenterImpl;
import com.hailer.news.home.view.ILoginView;
import com.hailer.news.util.bean.UserInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by moma on 17-7-31.
 */

public class ILoginPresenterImpl extends BasePresenterImpl<ILoginView,UserInfo> implements ILoginPresenter {

//    IContractUsInteractor<UserInfo> mContractUsInteractor;

    private UserInfo userinfo;

    public ILoginPresenterImpl(ILoginView view, UserInfo userinfo) {
        super(view);
        this.userinfo = userinfo;
    }

    private boolean isEmail(String email){
        final String patternStr = "^([a-z0-9A-Z]+[-|//.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?//.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(patternStr);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            return false;
        }
        return true;
    }

    private boolean isPhoneNumber(String str){
        return false;
    }


    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public String IsCorrect() {
        return null;
    }
}
