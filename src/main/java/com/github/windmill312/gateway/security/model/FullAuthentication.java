package com.github.windmill312.gateway.security.model;

public class FullAuthentication {
    private String accessToken;
    private String refreshToken;
    private Principal principal;

    public FullAuthentication(
            String accessToken,
            String refreshToken,
            Principal principal) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.principal = principal;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Principal getPrincipal() {
        return principal;
    }
}
