package com.github.windmill312.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

public class LoginInfo {

    private String accessToken;
    private String refreshToken;

    public LoginInfo(
            String accessToken,
            String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @JsonGetter("refreshToken")
    public String getRefreshToken() {
        return refreshToken;
    }

    @JsonGetter("accessToken")
    public String getAccessToken() {
        return accessToken;
    }
}