package com.hailer.news.splash;

import java.util.Vector;

import rx.Observable;

/**
 * Created by moma on 17-8-16.
 */

public interface SplashContract {
    interface View {
        void showAdvertising();
    }
    interface Presenter {
        void getAdvertising();

        void startNewsActivity();
    }
    interface Model<T> {
        Observable<T> getAdvertising();
    }
}
