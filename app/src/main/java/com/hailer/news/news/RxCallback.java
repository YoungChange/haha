package com.hailer.news.news;

/**

 */
public interface RxCallback<T> {

    /**
     * 请求错误调用
     *
     * @param msgType 错误信息
     */
    void requestError(int msgType);


    /**
     * 请求成功调用
     *
     * @param data 数据
     */
    void requestSuccess(T data);
}
