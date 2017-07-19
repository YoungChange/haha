package com.moma.app.news.util.bean;

/**
 * Created by moma on 17-7-18.
 */

public class NewsChannelBean {
    private String tName;

    // 标签类型，显示是我的频道还是更多频道
    private int tabType;

    public NewsChannelBean(String tName) {
        this.tName = tName;
        this.tabType = 0;
    }

    public NewsChannelBean(String tName, int tabType) {
        this.tName = tName;
        this.tabType = tabType;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public int getTabType() {
        return tabType;
    }

    public void setTabType(int tabType) {
        this.tabType = tabType;
    }
}
