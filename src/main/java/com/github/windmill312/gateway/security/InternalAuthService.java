package com.github.windmill312.gateway.security;

import com.github.windmill312.auth.grpc.model.v1.GAuthenticateServiceRequest;
import com.github.windmill312.auth.grpc.model.v1.GFullAuthentication;
import com.github.windmill312.auth.grpc.model.v1.GGetAuthenticationRequest;
import com.github.windmill312.gateway.converter.AuthConverter;
import com.github.windmill312.gateway.grpc.client.GRpcAuthServiceClient;
import com.github.windmill312.gateway.security.model.FullAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InternalAuthService {

    private static final Logger logger = LoggerFactory.getLogger(InternalAuthService.class);

    private final GRpcAuthServiceClient rpcAuthServiceClient;
    private final GatewayCredentialsHolder credentialsHolder;

    private FullAuthentication internalAuthentication;

    @Autowired
    public InternalAuthService(
            GRpcAuthServiceClient rpcAuthServiceClient,
            GatewayCredentialsHolder credentialsHolder) {
        this.rpcAuthServiceClient = rpcAuthServiceClient;
        this.credentialsHolder = credentialsHolder;
    }

    public GFullAuthentication getGAuthentication() {
        return AuthConverter.toGFullAuthentication(getInternalAuthentication());
    }

    public FullAuthentication getInternalAuthentication() {
        if (internalAuthentication == null || isAuthenticationExpired()) {
            internalAuthentication = AuthConverter.toFullAuthentication(
                    rpcAuthServiceClient.authenticateService(
                            GAuthenticateServiceRequest.newBuilder()
                                    .setServiceId(credentialsHolder.getServiceId())
                                    .setServiceSecret(credentialsHolder.getServiceSecret())
                                    .build())
                            .getAuthentication());
        }

        return internalAuthentication;
    }

    private boolean isAuthenticationExpired() {
        try {
            rpcAuthServiceClient.getAuthentication(
                    GGetAuthenticationRequest.newBuilder()
                            .setAuthentication(AuthConverter.toGAuthentication(internalAuthentication))
                            .setToken(internalAuthentication.getAccessToken())
                            .build());

            return false;
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return true;
        }
    }
}
