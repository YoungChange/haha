package com.hailer.news.util.bean;

import java.sql.Time;
import java.util.StringTokenizer;

/**
 * Created by moma on 17-7-31.
 */

public class NewsComment {

    private String time;
    private String content;
    private String userName;
    private String userPicUrl;

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPicUrl() {
        return userPicUrl;
    }

    public NewsComment(String time, String content, String userName, String userPicUrl) {
        this.time = time;
        this.content = content;
        this.userName = userName;
        this.userPicUrl = userPicUrl;
    }

}
