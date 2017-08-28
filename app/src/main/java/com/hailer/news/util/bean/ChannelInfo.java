package com.hailer.news.util.bean;

/**
 * Created by moma on 17-8-28.
 */

public class ChannelInfo {
    private int id;
    private String category_name;
    private String category_slug;
    private String description;
    private boolean sign = false;

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return category_name;
    }

    public String getCategorySlug() {
        return category_slug;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }
}


