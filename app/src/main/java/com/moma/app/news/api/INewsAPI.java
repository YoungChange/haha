package com.moma.app.news.api;

import com.moma.app.news.api.bean.NewsDetail;
import com.moma.app.news.api.bean.NewsItem;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Fuction: 数据请求API接口<p>
 * CreateDate:2017-07-19<p>
 */
public interface INewsAPI {

    /**
     * 请求新闻列表 例子：http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
     * 192.168.8.234:8000/api/category/1/posts?token=7rRltNoGUM2TLA0avUeXbEyDPKvtYQMD&per_page=20&page=1
     *
     * @param type      新闻类别：headline为头条,local为北京本地,fangchan为房产,list为其他
     * @param id        新闻类别id
     * @param startPage 开始的页码
     * @return 被观察对象
     */
    /*
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsList>>> getNewsList(
            @Path("type") String type,
            @Path("id") String id,
            @Path("startPage") int startPage);
    */
    //@GET("{type}/{id}/posts?token=7rRltNoGUM2TLA0avUeXbEyDPKvtYQMD&per_page=20&page={startPage}")
    //@GET("{type}/{id}/posts/10/{startpage}")
    //@GET("category/1/posts/10/1")
    @GET("{type}/{id}/posts/10/{startPage}")
    Observable<Map<String, List<NewsItem>>> getNewsList(
            @Path("type") String type,
            @Path("id") String id,
            @Path("startPage") int startPage
    );

    /**
     * 新闻详情：例子：http://c.m.163.com/nc/article/BFNFMVO800034JAU/full.html
     *
     * @param postId 新闻详情的id
     * @return 被观察对象
     */
    @GET("post/{postId}")
    Observable<Map<String, NewsDetail>> getNewsDetail(
            @Path("postId") String postId
    );


}
