package com.moma.app.news.api.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Fuction: 新闻列表
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsItem {
    @JsonProperty("id")
    public int id;
    @JsonProperty("post_title")
    public String post_title;
    @JsonProperty("post_slug")
    public String post_slug;
    @JsonProperty("post_excerpt")
    public String post_excerpt;
    @JsonProperty("post_date")
    public String post_date;
    @JsonProperty("comment_count")
    public int comment_count;

    @JsonProperty("post_image")
    public Map<String, ImageEntity> post_image;
    @JsonProperty("post_image_list")
    public List<ImageEntity> post_image_list;


    @JsonProperty("post_author")
    public String post_author;
    @JsonProperty("url")
    public String url;

    @JsonProperty("pivot")
    public IVotEntity pivot;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IVotEntity {
        @JsonProperty("category_id")
        public int category_id;
        @JsonProperty("post_id")
        public int post_id;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImageEntity {
        @JsonProperty("url")
        public String imageUrl;
        @JsonProperty("width")
        public String imageWidth;
        @JsonProperty("height")
        public String imageHeight;
    }

    /*
    @JsonProperty("postid")
    public String postid;
    @JsonProperty("hasCover")
    public boolean hasCover;
    @JsonProperty("hasHead")
    public int hasHead;
    @JsonProperty("replyCount")
    public int replyCount;
    @JsonProperty("hasImg")
    public int hasImg;
    @JsonProperty("digest")
    public String digest;
    @JsonProperty("hasIcon")
    public boolean hasIcon;
    @JsonProperty("docid")
    public String docid;
    @JsonProperty("title")
    public String title;
    @JsonProperty("order")
    public int order;
    @JsonProperty("priority")
    public int priority;
    @JsonProperty("lmodify")
    public String lmodify;
    @JsonProperty("boardid")
    public String boardid;
    @JsonProperty("photosetID")
    public String photosetID;
    @JsonProperty("template")
    public String template;
    @JsonProperty("votecount")
    public int votecount;
    @JsonProperty("skipID")
    public String skipID;
    @JsonProperty("alias")
    public String alias;
    @JsonProperty("skipType")
    public String skipType;
    @JsonProperty("cid")
    public String cid;
    @JsonProperty("hasAD")
    public int hasAD;
    @JsonProperty("imgsrc")
    public String imgsrc;
    @JsonProperty("tname")
    public String tname;
    @JsonProperty("ename")
    public String ename;
    @JsonProperty("ptime")
    public String ptime;


    @JsonProperty("ads")
    public List<AdsEntity> ads;
    //imgsrc : http://img5.cache.netease.com/3g/2016/2/13/201602131446132dc50.jpg

    @JsonProperty("imgextra")
    public List<ImgextraEntity> imgextra;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AdsEntity {
        @JsonProperty("title")
        public String title;
        @JsonProperty("tag")
        public String tag;
        @JsonProperty("imgsrc")
        public String imgsrc;
        @JsonProperty("subtitle")
        public String subtitle;
        @JsonProperty("url")
        public String url;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImgextraEntity {
        @JsonProperty("imgsrc")
        public String imgsrc;
    }
    */
}
