package com.hailer.news.api.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;

/**
 * Fuction: 登录信息
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginInfo {
    @JsonProperty("status")
    public String status;
    @JsonProperty("token")
    public String token;
    @JsonProperty("user")
    public User userInfo;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User{
        @JsonProperty("facebook_id")
        public String platformId;
        @JsonProperty("avatar")
        public String avatar;
        @JsonProperty("name")
        public String name;
    }
}
