package com.hailer.news.api;

import android.util.SparseArray;

import com.hailer.news.NewsApplication;
import com.hailer.news.api.bean.CommentInfo;
import com.hailer.news.api.bean.LoginInfo;
import com.hailer.news.api.bean.NewsDetail;
import com.hailer.news.api.bean.NewsItem;
import com.hailer.news.util.NetworkUtil;
import com.hailer.news.util.bean.ChannelInfo;
import com.hailer.news.util.bean.UserInfo;
import com.hailer.news.util.bean.VersionInfo;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Fuction: Retrofit请求管理类<p>
 */
public class RetrofitService {

    // 设缓存有效期为两天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    // 30秒内直接读缓存
    private static final long CACHE_AGE_SEC = 0;

    private static volatile OkHttpClient sOkHttpClient;
    // 管理不同HostType的单例
    private static SparseArray<RetrofitService> sInstanceManager = new SparseArray<>(1);
    private INewsAPI mNewsAPI;

    //cache size
    private static final long CACHE_FILE_SIZE = 1024*1024*100;

    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            // 在这里统一配置请求头缓存策略以及响应头缓存策略
            if (NetworkUtil.isConnected(NewsApplication.getContext())) {
                // 在有网的情况下CACHE_AGE_SEC秒内读缓存，大于CACHE_AGE_SEC秒后会重新请求数据
                request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
                Response response = chain.proceed(request);
                return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
            } else {
                // 无网情况下CACHE_STALE_SEC秒内读取缓存，大于CACHE_STALE_SEC秒缓存无效报504
                request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
                Response response = chain.proceed(request);
                return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
            }

        }
    };

    // 打印返回的json数据拦截器
    private Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();

            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
            request = requestBuilder.build();

            final Response response = chain.proceed(request);

            KLog.v("request.url() \n" + request.url() + " \n " + "request.headers()：\n" + request.headers() + "response.headers()：\n" + response.headers());

            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    KLog.e("");
                    KLog.e("Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }

            if (contentLength != 0) {
                KLog.v("--------------------------------------------开始打印返回数据----------------------------------------------------");
                KLog.json(buffer.clone().readString(charset));
                KLog.v("--------------------------------------------结束打印返回数据----------------------------------------------------");
            }

            return response;
        }
    };

    private RetrofitService() {
        //null
    }

    private RetrofitService(int hostType) {
        //配置OKHttpClient
        if (sOkHttpClient == null) {
            synchronized (RetrofitService.class) {
                if (sOkHttpClient == null) {
                    // OkHttpClient配置是一样的,静态创建一次即可
                    // 指定缓存路径,缓存大小100Mb
                    File file =  new File(
                            NewsApplication
                            .getContext()
                            .getCacheDir(), "HttpCache");
                    Cache cache = new Cache(new File(NewsApplication.getContext().getCacheDir(), "HttpCache"), CACHE_FILE_SIZE);

                    sOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mLoggingInterceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(3, TimeUnit.SECONDS)
                            .build();

                }
            }
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConfig.HOST_NAME)
                .client(sOkHttpClient)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mNewsAPI = retrofit.create(INewsAPI.class);
    }

    /**
     * 获取单例
     *
     * @param hostType host类型
     * @return 实例
     */
    public static RetrofitService getInstance(int hostType) {
        RetrofitService instance = sInstanceManager.get(hostType);
        if (instance == null) {
            instance = new RetrofitService(hostType);
            sInstanceManager.put(hostType, instance);
            return instance;
        } else {
            return instance;
        }
    }


    /**
     * 网易新闻列表
     * 对API调用了observeOn(MainThread)之后，线程会跑在主线程上，包括onComplete也是，
     * unsubscribe也在主线程，然后如果这时候调用call.cancel会导致NetworkOnMainThreadException
     * 加一句unsubscribeOn(io)
     *
     * @param catid        新闻类别id
     * @param startPage 第几页
     */
    public Observable<Map<String, List<NewsItem>>> getNewsListObservable(String catid, int startPage) {
        return mNewsAPI.getNewsList(catid, APIConfig.GET_TOKEN, APIConfig.LIST_ITEMS_PER_PAGE, startPage)
                .compose(new BaseSchedulerTransformer<Map<String, List<NewsItem>>>());
    }


    public Observable<Map<String,List<ChannelInfo>>> getChannelListObservable(){
        return mNewsAPI.getChannelList(APIConfig.GET_TOKEN, 20, 1)
                .compose(new BaseSchedulerTransformer<Map<String,List<ChannelInfo>>>());
    }


    /**
     * 获取新闻详情
     *
     * @param postId 新闻详情的id
     */
    public Observable<Map<String, NewsDetail>> getNewsDetailObservable(String postId) {
        return mNewsAPI.getNewsDetail(postId, APIConfig.GET_TOKEN)
                .compose(new BaseSchedulerTransformer<Map<String, NewsDetail>>());
    }

    /**
     * 反馈
     */
    public Observable<String> postFeedbackObservable(String email, String feedback) {
        KLog.e("postFeedback, email="+email+", feedback="+feedback);
        return mNewsAPI.postFeedback(email, feedback, APIConfig.GET_TOKEN)
                .compose(new BaseSchedulerTransformer<String>());
    }

    /**
     * 登录
     */
    public Observable<LoginInfo> loginObservable(UserInfo userInfo) {
        KLog.e("------------------loginObservable---userInfo.getPlatformToken():"+userInfo.getPlatformToken());
        return mNewsAPI.login(userInfo.getPlatformToken(), "test@magicmoma.com", "test")
                .compose(new BaseSchedulerTransformer<LoginInfo>());
    }

    /**
     * 登录
     */
    public Observable<String> postCommentObservable(String postId, String serverToken, String comment) {
        return mNewsAPI.postCommnet(postId, serverToken, comment)
                .compose(new BaseSchedulerTransformer<String>());
    }

    /**
     * 评论列表
     */
    public Observable<Map<String, List<CommentInfo>>> getCommentsListObservable(String postId, int startPage) {
        return mNewsAPI.getCommentsList(postId, APIConfig.GET_TOKEN, APIConfig.LIST_ITEMS_PER_PAGE, startPage)
                .compose(new BaseSchedulerTransformer<Map<String, List<CommentInfo>>>());
    }

    /**
     * 点赞功能
     * @param commentId 评论的id
     * @param token
     * @return
     */
    public Observable<Boolean> postVoteObservable(String commentId, String token) {
        return mNewsAPI.postVoteComment(commentId, token)
                .compose(new BaseSchedulerTransformer<Boolean>());
    }

    public Observable<VersionInfo> getVersionInfoObservable() {
        return mNewsAPI.getVersionInfo(APIConfig.GET_TOKEN,APIConfig.DEVICE_TYPE)
                .compose(new BaseSchedulerTransformer<VersionInfo>());
    }


}
