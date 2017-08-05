package com.hailer.news.api;

import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.api.bean.LoginInfo;
import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.api.bean.NewsItem;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Fuction: 数据请求API接口<p>
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
            @Query("page") int pageNumber
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

    /**
     * 用户反馈：
     * hailer.news/api/feedback/store/?token=7rRltNoGUM2TLA0avUeXbEyDPKvtYQMD
     * @param email 用户地址
     * @param feedback 用户反馈
     */
    @FormUrlEncoded
    @POST("feedback/store/")
    Observable<String> postFeedback(
            @Field("email") String email,
            @Field("content") String feedback,
            @Query("token") String token
    );

    /**
     * 用户反馈：
     * hailer.news/api/feedback/store/?token=7rRltNoGUM2TLA0avUeXbEyDPKvtYQMD
     */
    @FormUrlEncoded
    @POST("auth/login/")
    Observable<LoginInfo> login(
            @Field("fb_token") String token,
            @Field("email") String email,
            @Field("password") String passwd
    );

    /**
     * 用户反馈：
     * hailer.news/api/feedback/store/?token=7rRltNoGUM2TLA0avUeXbEyDPKvtYQMD
     */
    @FormUrlEncoded
    @POST("comment/store/{postId}")
    Observable<String> postCommnet(
            @Path("postId") String postId,
            @Query("token") String token,
            @Field("comment") String comment
    );

    /**
     * 用户反馈：
     * hailer.news/api/feedback/store/?token=7rRltNoGUM2TLA0avUeXbEyDPKvtYQMD
     */
    @GET("post/{postId}/comments")
    Observable<Map<String, List<CommentInfo>>> getCommentsList(
            @Path("postId") String postId,
            @Query("token") String token,
            @Query("per_page") int perPage,
            @Query("page_num") int pageNumber
    );


}
