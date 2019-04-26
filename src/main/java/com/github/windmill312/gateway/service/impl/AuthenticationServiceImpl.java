package com.github.windmill312.gateway.service.impl;

import com.github.windmill312.auth.grpc.model.v1.GAuthenticateAnyRequest;
import com.github.windmill312.auth.grpc.model.v1.GGenerateTokenRequest;
import com.github.windmill312.auth.grpc.model.v1.GGetAuthenticationRequest;
import com.github.windmill312.auth.grpc.model.v1.GGetPrincipalOuterKeyRequest;
import com.github.windmill312.auth.grpc.model.v1.GPrincipalOuterKey;
import com.github.windmill312.auth.grpc.model.v1.GRevokeAuthenticationRequest;
import com.github.windmill312.auth.grpc.model.v1.GToken;
import com.github.windmill312.gateway.annotation.GatewayService;
import com.github.windmill312.gateway.converter.AuthConverter;
import com.github.windmill312.gateway.grpc.client.GRpcAuthServiceClient;
import com.github.windmill312.gateway.grpc.client.GRpcCredentialsServiceClient;
import com.github.windmill312.gateway.security.InternalAuthService;
import com.github.windmill312.gateway.security.TokenConfig;
import com.github.windmill312.gateway.security.model.Authentication;
import com.github.windmill312.gateway.security.model.AuthenticationToken;
import com.github.windmill312.gateway.security.model.FullAuthentication;
import com.github.windmill312.gateway.security.model.Principal;
import com.github.windmill312.gateway.service.AuthenticationService;
import com.github.windmill312.gateway.web.to.in.LoginCustomerRequest;
import com.github.windmill312.gateway.web.to.out.CustomerInfo;
import com.github.windmill312.gateway.web.to.out.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.github.windmill312.gateway.exception.model.Service.AUTH;

@GatewayService(serviceName = AUTH)
public class AuthenticationServiceImpl implements AuthenticationService {

    private final InternalAuthService internalAuthService;
    private final TokenConfig tokenConfig;
    private final GRpcAuthServiceClient rpcAuthServiceClient;
    private final GRpcCredentialsServiceClient rpcCredentialsServiceClient;

    @Autowired
    public AuthenticationServiceImpl(
            InternalAuthService internalAuthService,
            TokenConfig tokenConfig,
            GRpcAuthServiceClient rpcAuthServiceClient,
            GRpcCredentialsServiceClient rpcCredentialsServiceClient) {

        this.internalAuthService = internalAuthService;
        this.tokenConfig = tokenConfig;
        this.rpcAuthServiceClient = rpcAuthServiceClient;
        this.rpcCredentialsServiceClient = rpcCredentialsServiceClient;
    }

    @Override
    public LoginInfo login(
            LoginCustomerRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        GPrincipalOuterKey principalKey = getPrincipalKey(request);
        GToken token = generateToken();

        FullAuthentication authentication = AuthConverter.toFullAuthentication(
                rpcAuthServiceClient.authenticateAny(
                        GAuthenticateAnyRequest.newBuilder()
                                .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                                .setToken(token)
                                .setPrincipalKey(principalKey)
                                .build())
                        .getAuthentication());

        return new LoginInfo(authentication.getAccessToken(), authentication.getRefreshToken());
    }

    @Override
    public void logout(AuthenticationToken authentication) {
        rpcAuthServiceClient.revokeAuthentication(
                GRevokeAuthenticationRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setToken(authentication.getToken())
                        .build());
    }

    @Override
    public Authentication getAuthentication(String token) {
        return AuthConverter.toAuthentication(
                rpcAuthServiceClient.getAuthentication(
                        GGetAuthenticationRequest.newBuilder()
                                .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                                .setToken(token)
                                .build())
                        .getAuthentication());
    }

    @Override
    public CustomerInfo getCustomerInfo(Principal principal) {
        return new CustomerInfo(principal.getExtId());
    }

    private GPrincipalOuterKey getPrincipalKey(LoginCustomerRequest request) {
        return rpcCredentialsServiceClient.getPrincipalOuterKey(
                GGetPrincipalOuterKeyRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setCredentials(AuthConverter.toGCredentials(request))
                        .build())
                .getPrincipalKey();
    }

    private GToken generateToken() {
        return rpcAuthServiceClient.generateToken(
                GGenerateTokenRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setTokenTtlSeconds(tokenConfig.getTokenTtlSeconds())
                        .build())
                .getToken();
    }
}
