package com.hailer.news.api.bean;

import com.hailer.news.api.APIConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Fuction: 新闻列表
 */

public class NewsItem {

    private int id;
    private String post_title;
    private String url;
    private int comment_count;
    private Map<String, ImageEntity> post_image;
    private List<ImageEntity> post_image_list;
    private String post_date;

    private ArrayList<String> mImageList = new ArrayList<>();

    public static class ImageEntity {
        public String url;
        public String width;
        public String height;
    }

    public int getPostId(){
        return id;
    }

    public String getPostTitle() {
        return post_title;
    }

    public String getPostUrl(){
        return url;
    }

    public int getCommentsCount() {
        return comment_count;
    }

    public String getDate() {
        return post_date;
    }

    public ArrayList<String> getImageList() {
        if (mImageList.size() > 0) {
            return mImageList;
        }

        if (post_image_list != null && !post_image_list.isEmpty()) {
            for (ImageEntity imageEntity : post_image_list) {
                mImageList.add(imageEntity.url);
            }
        } else if (post_image != null && !post_image.isEmpty()){
            mImageList.add(post_image.get("small").url);
        }
        return mImageList;
    }
}
