package com.github.windmill312.gateway.service;

import com.github.windmill312.gateway.security.model.AuthenticationToken;
import com.github.windmill312.gateway.web.to.in.OAuthRefreshTokenRequest;
import com.github.windmill312.gateway.web.to.in.OAuthTokenRequest;
import com.github.windmill312.gateway.web.to.out.OAuthCodeResponse;
import com.github.windmill312.gateway.web.to.out.OAuthTokenResponse;

public interface OAuthService {

    OAuthCodeResponse authorize(String clientId, AuthenticationToken authentication);

    OAuthTokenResponse getToken(OAuthTokenRequest request);

    OAuthTokenResponse refreshToken(OAuthRefreshTokenRequest request);
}
