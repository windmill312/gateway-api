package com.github.windmill312.gateway.service.impl;

import com.github.windmill312.auth.grpc.model.v1.GAuthenticateAnyRequest;
import com.github.windmill312.auth.grpc.model.v1.GDeletePrincipalRequest;
import com.github.windmill312.auth.grpc.model.v1.GGenerateTokenRequest;
import com.github.windmill312.auth.grpc.model.v1.GGetAuthenticationRequest;
import com.github.windmill312.auth.grpc.model.v1.GGetPrincipalIdentifierRequest;
import com.github.windmill312.auth.grpc.model.v1.GGetPrincipalOuterKeyRequest;
import com.github.windmill312.auth.grpc.model.v1.GPrincipalOuterKey;
import com.github.windmill312.auth.grpc.model.v1.GRevokeAuthenticationRequest;
import com.github.windmill312.auth.grpc.model.v1.GToken;
import com.github.windmill312.auth.grpc.model.v1.GUpdateTokenRequest;
import com.github.windmill312.common.grpc.model.GUuid;
import com.github.windmill312.gateway.annotation.GatewayService;
import com.github.windmill312.gateway.converter.AuthConverter;
import com.github.windmill312.gateway.grpc.client.GRpcAuthServiceClient;
import com.github.windmill312.gateway.grpc.client.GRpcCredentialsServiceClient;
import com.github.windmill312.gateway.grpc.client.GRpcPrincipalServiceClient;
import com.github.windmill312.gateway.security.InternalAuthService;
import com.github.windmill312.gateway.security.TokenConfig;
import com.github.windmill312.gateway.security.model.Authentication;
import com.github.windmill312.gateway.security.model.AuthenticationToken;
import com.github.windmill312.gateway.security.model.FullAuthentication;
import com.github.windmill312.gateway.security.model.Principal;
import com.github.windmill312.gateway.service.AuthenticationService;
import com.github.windmill312.gateway.web.to.in.LoginCustomerRequest;
import com.github.windmill312.gateway.web.to.in.UpdateTokenRequest;
import com.github.windmill312.gateway.web.to.out.CustomerFullInfo;
import com.github.windmill312.gateway.web.to.out.IdentifierResponse;
import com.github.windmill312.gateway.web.to.out.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.github.windmill312.gateway.exception.model.Service.AUTH;

@GatewayService(serviceName = AUTH)
public class AuthenticationServiceImpl implements AuthenticationService {

    private final InternalAuthService internalAuthService;
    private final CustomerServiceImpl customerService;
    private final TokenConfig tokenConfig;
    private final GRpcAuthServiceClient rpcAuthServiceClient;
    private final GRpcCredentialsServiceClient rpcCredentialsServiceClient;
    private final GRpcPrincipalServiceClient rpcPrincipalServiceClient;

    @Autowired
    public AuthenticationServiceImpl(
            InternalAuthService internalAuthService,
            CustomerServiceImpl customerService,
            TokenConfig tokenConfig,
            GRpcAuthServiceClient rpcAuthServiceClient,
            GRpcCredentialsServiceClient rpcCredentialsServiceClient,
            GRpcPrincipalServiceClient rpcPrincipalServiceClient) {

        this.internalAuthService = internalAuthService;
        this.customerService = customerService;
        this.tokenConfig = tokenConfig;
        this.rpcAuthServiceClient = rpcAuthServiceClient;
        this.rpcCredentialsServiceClient = rpcCredentialsServiceClient;
        this.rpcPrincipalServiceClient = rpcPrincipalServiceClient;
    }

    @Override
    public LoginInfo login(
            LoginCustomerRequest request) {

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

        return new LoginInfo(authentication.getAccessToken(), authentication.getRefreshToken(), authentication.getPrincipal().getExtId());
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
    public CustomerFullInfo getCustomerInfo(Principal principal) {
        return customerService.getCustomerByUid(principal.getExtId());
    }

    @Override
    public LoginInfo refreshToken(UpdateTokenRequest request) {
        GToken token = generateToken();

        FullAuthentication authentication = AuthConverter.toFullAuthentication(
                rpcAuthServiceClient.updateToken(
                        GUpdateTokenRequest.newBuilder()
                                .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                                .setToken(token)
                                .setPrincipalKey(
                                        GPrincipalOuterKey.newBuilder()
                                                .setSubsystemCode("20")
                                                .setExtId(request.getCustomerUid().toString())
                                                .build())
                                .build())
                        .getAuthentication());

        return new LoginInfo(authentication.getAccessToken(), authentication.getRefreshToken(), authentication.getPrincipal().getExtId());
    }

    @Override
    public IdentifierResponse getIdentifier(UUID principalUid) {
        return AuthConverter.convert(rpcCredentialsServiceClient.getIdentifier(
                GGetPrincipalIdentifierRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                .setPrincipalUid(GUuid.newBuilder().setUuid(principalUid.toString()).build())
                .build()));
    }

    @Override
    public void removePrincipal(UUID principalExtId) {
        rpcPrincipalServiceClient.deletePrincipal(
                GDeletePrincipalRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setPrincipalExtId(principalExtId.toString())
                        .build());
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
