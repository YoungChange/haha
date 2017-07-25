package com.moma.app.news.api;

/**
 * Fuction: 请求接口的配置<p>
 */
public class APIConfig {
    //api url
    public static final String HOST_NAME = "http://192.168.8.234:8000/api/";

    //token
    public static final String GET_TOKEN = "7rRltNoGUM2TLA0avUeXbEyDPKvtYQMD";

    //列表每次请求的新闻数量
    public static final int LIST_ITEMS_PER_PAGE = 20;

    //host type
    public static final int HOST_TYPE_NEWS = 1;

    //
    public static final String NEWS_DATA_JSON_KEY = "data";
}
