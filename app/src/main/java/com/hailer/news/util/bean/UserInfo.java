package com.hailer.news.util.bean;

import android.net.Uri;

/**
 * Created by moma on 17-7-31.
 */

public class UserInfo {

    private String platformId;
    private String name;
    private String platformToken;
    private Uri iconUri;
    private String serverToken;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatformToken() {
        return this.platformToken;
    }

    public void setPlatformToken(String token) {
        this.platformToken = token;
    }

    public String getPlatformId() {
        return this.platformId;
    }

    public void setPlatformId(String id) {
        this.platformId = id;
    }

    public Uri getIconUri() {
        return this.iconUri;
    }

    public void setIconUri(Uri iconUri) {
        this.iconUri = iconUri;
    }

    public String getServerToken() {
        return this.serverToken;
    }

    public void setServerToken(String token) {
        this.serverToken = token;
    }

    public UserInfo() {

    }
}
