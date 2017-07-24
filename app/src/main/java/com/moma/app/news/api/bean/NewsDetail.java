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
    @JsonProperty("title")
    public String title;
    @JsonProperty("excerpt")
    public String excerpt;
    @JsonProperty("date")
    public String date;
    @JsonProperty("comment_count")
    public int comment_count;
    @JsonProperty("image")
    public String image;
    @JsonProperty("content")
    public String content;
    @JsonProperty("author")
    public String author;

}
