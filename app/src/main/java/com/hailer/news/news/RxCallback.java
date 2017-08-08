package com.hailer.news.news;

/**

 */
public interface RxCallback<T> {

    /**
     * 请求错误调用
     *
     * @param msg 错误信息
     */
    void requestError(String msg);


    /**
     * 请求成功调用
     *
     * @param data 数据
     */
    void requestSuccess(T data);
}
