package com.hailer.news.api.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Fuction: 每条新闻的详情<p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentInfo {

    @JsonProperty("id")
    public int id;
    @JsonProperty("post_id")
    public int post_id;
    @JsonProperty("comment_date")
    public String comment_date;
    @JsonProperty("comment_content")
    public String comment_content;
    @JsonProperty("post_date")
    public String date;
    @JsonProperty("comment_count")
    public int comment_count;
    @JsonProperty("post_author")
    public String author;
    @JsonProperty("post_content")
    public String content;
    @JsonProperty("user")
    public CommentUser commnetUser;


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CommentUser {
        @JsonProperty("id")
        public int user_id;
        @JsonProperty("avatar")
        public String avatar;
        @JsonProperty("name")
        public String name;
    }
}
