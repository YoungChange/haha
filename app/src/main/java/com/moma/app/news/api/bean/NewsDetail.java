package com.moma.app.news.api.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Fuction: 每条新闻的详情<p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsDetail {

    @JsonProperty("id")
    public int id;
    @JsonProperty("post_title")
    public String title;
    @JsonProperty("post_slug")
    public String slug;
    @JsonProperty("post_excerpt")
    public String excerpt;
    @JsonProperty("post_date")
    public String date;
    @JsonProperty("comment_count")
    public int comment_count;
    @JsonProperty("post_author")
    public String author;
    @JsonProperty("post_content")
    public String content;
}
