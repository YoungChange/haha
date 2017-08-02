package com.hailer.news.api.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Fuction: 登录信息
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginInfo {
    @JsonProperty("status")
    public String status;
    @JsonProperty("token")
    public String token;
}
