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
    private int comment_like;
    private boolean voted;
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
        if (user == null) {
            return null;
        }
        return user.name;
    }

    public String getUserAvatar(){
        if (user == null) {
            return null;
        }
        return user.avatar;
    }

    public int getCommentLike() {
        return comment_like < 0 ? 0 : comment_like;
    }

    public CommentInfo setCommentLike(int num) {
        comment_like = num < 0 ? 0 : num;
        return this;
    }

    public boolean isVoted() {
        return voted;
    }

    public CommentInfo setVote(boolean vote) {
        voted = vote;
        return this;
    }
}
