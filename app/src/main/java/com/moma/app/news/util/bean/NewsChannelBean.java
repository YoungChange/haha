package com.moma.app.news.util.bean;

/**
 * Created by moma on 17-7-18.
 */

public class NewsChannelBean {
    private String tName;

    private String tabType;  //T1348647909107

    public NewsChannelBean(String tName) {
        this.tName = tName;
        this.tabType = "T1348647909107";
    }

    public NewsChannelBean(String tName, String tabType) {
        this.tName = tName;
        this.tabType = tabType;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getTabType() {
        return tabType;
    }

    public void setTabType(String tabType) {
        this.tabType = tabType;
    }
}
