package com.hailer.news.common;

/**
 * Created by moma on 17-8-23.
 */

public interface ActivityCode {
    public interface Request{
        int TO_TWEET_COMPOSER_REQUEST_CODE = 101;

        int NEWS_DETAIL_TO_LOGIN_REQUEST_CODE = 1;
        int NEWS_DETAIL_TO_NEWS_COMMENT_REQUEST_CODE = 2;

        int NEWS_COMMENT_TO_LOGIN_REQUEST_CODE = 1;
    }

    public interface Result{
        int NEWS_COMMENT_RESULT_CODE = 1;
    }
}
