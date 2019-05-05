package com.github.windmill312.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class LoginInfo {

    private String accessToken;
    private String refreshToken;
    private UUID customerUid;

    public LoginInfo(
            String accessToken,
            String refreshToken,
            UUID customerUid) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.customerUid = customerUid;
    }

    @JsonGetter("refreshToken")
    public String getRefreshToken() {
        return refreshToken;
    }

    @JsonGetter("accessToken")
    public String getAccessToken() {
        return accessToken;
    }

    @JsonGetter("customerUid")
    public UUID getCustomerUid() {
        return customerUid;
    }
}