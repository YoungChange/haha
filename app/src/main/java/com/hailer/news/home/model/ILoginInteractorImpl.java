package com.hailer.news.home.model;

import com.hailer.news.api.APIConfig;
import com.hailer.news.api.RetrofitService;
import com.hailer.news.api.bean.LoginInfo;
import com.hailer.news.base.BaseSubscriber;
import com.hailer.news.util.bean.UserInfo;
import com.hailer.news.util.callback.RequestCallback;
import com.socks.library.KLog;

import java.util.Map;

import rx.Subscription;
import rx.functions.Func1;

/**
 * Fuction: 新闻详情的Model层接口实现<p>
 */
public class ILoginInteractorImpl implements ILoginInteractor<String> {

    @Override
    public Subscription login(final RequestCallback<String> callback, final UserInfo userInfo) {
        return RetrofitService.getInstance(APIConfig.HOST_TYPE_NEWS).login(userInfo)

                .map(new Func1<LoginInfo, String>() {
                    @Override
                    public String call(LoginInfo info) {
                        KLog.e("-----------bailei---success, token="+info.token);
                        return info.token;
                    }
                })

                .subscribe(new BaseSubscriber<String>(callback));
    }

}
