package com.hailer.news.util.bean;

/**
 * Created by moma on 17-7-31.
 */

public class UserInfo {

    private String Name;
    private String password;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserInfo(String name, String password) {
        Name = name;
        this.password = password;
    }
}
