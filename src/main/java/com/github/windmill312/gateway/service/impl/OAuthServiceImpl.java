package com.github.windmill312.gateway.service.impl;

import com.github.windmill312.auth.grpc.model.v1.GOAuthAuthorizeRequest;
import com.github.windmill312.auth.grpc.model.v1.GOAuthRefreshTokenRequest;
import com.github.windmill312.auth.grpc.model.v1.GOAuthTokenRequest;
import com.github.windmill312.gateway.annotation.GatewayService;
import com.github.windmill312.gateway.converter.AuthConverter;
import com.github.windmill312.gateway.grpc.client.GRpcOAuthServiceClient;
import com.github.windmill312.gateway.security.InternalAuthService;
import com.github.windmill312.gateway.security.model.AuthenticationToken;
import com.github.windmill312.gateway.service.OAuthService;
import com.github.windmill312.gateway.web.to.in.OAuthRefreshTokenRequest;
import com.github.windmill312.gateway.web.to.in.OAuthTokenRequest;
import com.github.windmill312.gateway.web.to.out.OAuthCodeResponse;
import com.github.windmill312.gateway.web.to.out.OAuthTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.windmill312.gateway.exception.model.Service.AUTH;

@GatewayService(serviceName = AUTH)
public class OAuthServiceImpl implements OAuthService {

    private final InternalAuthService internalAuthService;
    private final GRpcOAuthServiceClient rpcOAuthServiceClient;

    @Autowired
    public OAuthServiceImpl(
            InternalAuthService internalAuthService,
            GRpcOAuthServiceClient rpcOAuthServiceClient) {

        this.internalAuthService = internalAuthService;
        this.rpcOAuthServiceClient = rpcOAuthServiceClient;
    }

    @Override
    public OAuthCodeResponse authorize(String clientId, AuthenticationToken authentication) {
        return new OAuthCodeResponse(
                rpcOAuthServiceClient.authorize(
                        GOAuthAuthorizeRequest.newBuilder()
                                .setAuthentication(internalAuthService.getGAuthentication())
                                .setClientId(clientId)
                                .setUserAccessToken(authentication.getToken())
                                .build())
                        .getAuthorizationCode());
    }

    @Override
    public OAuthTokenResponse getToken(OAuthTokenRequest request) {
        return AuthConverter.convert(
                rpcOAuthServiceClient.getToken(
                        GOAuthTokenRequest.newBuilder()
                                .setAuthentication(internalAuthService.getGAuthentication())
                                .setClientId(request.getClientId())
                                .setClientSecret(request.getClientSecret())
                                .setAuthorizationCode(request.getAuthorizationCode())
                                .build()));
    }

    @Override
    public OAuthTokenResponse refreshToken(OAuthRefreshTokenRequest request) {
        return AuthConverter.convert(
                rpcOAuthServiceClient.refreshToken(
                        GOAuthRefreshTokenRequest.newBuilder()
                                .setAuthentication(internalAuthService.getGAuthentication())
                                .setClientId(request.getClientId())
                                .setClientSecret(request.getClientSecret())
                                .setRefreshToken(request.getRefreshToken())
                                .build()));
    }
}
