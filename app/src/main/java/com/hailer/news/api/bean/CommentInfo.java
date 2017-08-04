package com.hailer.news.api.bean;

/**
 * Fuction: 每条新闻的详情<p>
 */

public class CommentInfo {
    private int id;
    private int post_id;
    private String comment_date;
    private String comment_content;
    private String post_date;
    private CommentUser user;


    public static class CommentUser {
        public int id;
        public String avatar;
        public String name;
    }

    public int getId (){
        return id;
    }

    public String getComment(){
        return comment_content;
    }

    public String getDate(){
        return comment_date;
    }

    public String getUserName() {
        return user.name;
    }

    public String getUserAvatar(){
        return user.avatar;
    }
}
