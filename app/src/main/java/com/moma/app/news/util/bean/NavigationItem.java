package com.moma.app.news.util.bean;

/**
 * Created by moma on 17-7-24.
 */

public class NavigationItem {
    private int itemTitle;
    private int itemImageId;

    public NavigationItem(int itemTitle, int itemImageId) {
        this.itemTitle = itemTitle;
        this.itemImageId = itemImageId;
    }

    public int getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(int itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getItemImageId() {
        return itemImageId;
    }

    public void setItemImageId(int itemImageId) {
        this.itemImageId = itemImageId;
    }
}