package com.moma.app.news.api;

import com.moma.app.news.api.bean.NewsDetail;
import com.moma.app.news.api.bean.NewsItem;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Fuction: 数据请求API接口<p>
 * CreateDate:2017-07-19<p>
 */
public interface INewsAPI {

    /**
     * 请求新闻列表 :
     * 192.168.8.234:8000/api/category/1/posts?token=7rRltNoGUM2TLA0avUeXbEyDPKvtYQMD&per_page=20&page=1
     *
     * @param catid     category id
     * @param token     token
     * @param perPage   每次请求获取的新闻数量
     * @param pageNumber 请求的第几页
     */
    @GET("category/{catid}/posts")
    Observable<Map<String, List<NewsItem>>> getNewsList(
            @Path("catid") String catid,
            @Query("token") String token,
            @Query("per_page") int perPage,
            @Query("pageNumber") int pageNumber
    );

    /**
     * 新闻详情：
     * 192.168.8.234:8000/api/post/2?token=7rRltNoGUM2TLA0avUeXbEyDPKvtYQMD
     * @param postId 新闻详情的id
     * @param token token
     */
    @GET("post/{postId}")
    Observable<Map<String, NewsDetail>> getNewsDetail(
            @Path("postId") String postId,
            @Query("token") String token
    );


}
