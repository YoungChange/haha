package com.hailer.news.base.presenter;

/**
 * Fuction: 代理的基类<p>
 */
public interface DataCallback<T> {

    void onSuccess(T data);

    void onError(String error);

}
