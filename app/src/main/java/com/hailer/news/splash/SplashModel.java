package com.hailer.news.splash;

import rx.Observable;

/**
 * Created by moma on 17-8-16.
 */

public class SplashModel implements SplashContract.Model{
    @Override
    public Observable getAdvertising() {
        return Observable.just("AD");
    }
}
