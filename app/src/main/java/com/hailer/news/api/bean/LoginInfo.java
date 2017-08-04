package com.hailer.news.api.bean;

/**
 * Fuction: 登录信息
 */
public class LoginInfo {
    private String status;
    private String token;
    private User user;

    public static class User{
        public String facebook_id;
        public String avatar;
        public String name;
    }

    public String getToken(){
        return token;
    }

    public String getUserAvatar(){
        return user.avatar;
    }

    public String getUserName() {
        return user.name;
    }

    public String getUserId(){
        return user.facebook_id;
    }
}
