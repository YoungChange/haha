package com.hailer.news.util.bean;


public class NewsChannelBean {
    private String tabName;

    private String tabId;

    private String tabType;

    public NewsChannelBean(String tabName) {
        this.tabName = tabName;
        this.tabType = "category";
        this.tabId = "0";

    }

    public NewsChannelBean(String tabName, String tabType) {
        this.tabName = tabName;
        this.tabType = tabType;
        this.tabId = "0";
    }


    public NewsChannelBean(String tabName, String tabId, String tabType) {
        this.tabName = tabName;
        this.tabId = tabId;
        this.tabType = tabType;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }


    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public String getTabType() {
        return tabType;
    }

    public void setTabType(String tabType) {
        this.tabType = tabType;
    }
}
