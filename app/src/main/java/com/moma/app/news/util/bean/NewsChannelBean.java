package com.moma.app.news.util.bean;

/**
 * Created by moma on 17-7-18.
 */

public class NewsChannelBean {
    private String tabName;// 头条 科技 。。

    private String tabId;// T1348647909107

    private String tabType;//list  or   headline

    public NewsChannelBean(String tabName) {
        this.tabName = tabName;
        this.tabType = "headline";
        this.tabId = "T1348647909107";

    }

    public NewsChannelBean(String tabName, String tabType) {
        this.tabName = tabName;
        this.tabType = tabType;
        this.tabId = "T1348647909107";
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
