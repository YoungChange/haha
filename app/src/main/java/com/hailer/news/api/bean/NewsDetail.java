package com.hailer.news.api.bean;


import java.net.URL;

/**
 * Fuction: 每条新闻的详情
 */
public class NewsDetail {
    private int id;
    private String post_title;
    private String post_date;
    public int comment_count;
    private String post_content;
    private String url;

    public int getId() {
        return id;
    }

    public String getTitle(){
        return post_title;
    }

    public String getDate(){
        return post_date;
    }

    public int getCommentsCount(){
        return comment_count;
    }

    public String getContent() {
        return post_content;
    }

    public String getUrl() {
        return url;
    }
}
