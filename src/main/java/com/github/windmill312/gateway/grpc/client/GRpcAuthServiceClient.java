package com.github.windmill312.gateway.grpc.client;

import com.github.windmill312.auth.grpc.model.v1.GAuthenticateAnyRequest;
import com.github.windmill312.auth.grpc.model.v1.GAuthenticateAnyResponse;
import com.github.windmill312.auth.grpc.model.v1.GAuthenticateServiceRequest;
import com.github.windmill312.auth.grpc.model.v1.GAuthenticateServiceResponse;
import com.github.windmill312.auth.grpc.model.v1.GGenerateTokenRequest;
import com.github.windmill312.auth.grpc.model.v1.GGenerateTokenResponse;
import com.github.windmill312.auth.grpc.model.v1.GGetAuthenticationRequest;
import com.github.windmill312.auth.grpc.model.v1.GGetAuthenticationResponse;
import com.github.windmill312.auth.grpc.model.v1.GGetPrincipalIdentifierRequest;
import com.github.windmill312.auth.grpc.model.v1.GGetPrincipalIdentifierResponse;
import com.github.windmill312.auth.grpc.model.v1.GRevokeAuthenticationRequest;
import com.github.windmill312.auth.grpc.model.v1.GUpdateTokenRequest;
import com.github.windmill312.auth.grpc.service.v1.AuthServiceV1Grpc;
import com.github.windmill312.common.grpc.model.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GRpcAuthServiceClient {

    private AuthServiceV1Grpc.AuthServiceV1BlockingStub authServiceV1BlockingStub;

    @Value("#{new String('${gateway.grpc.client.AuthServiceV1Grpc.host}')}")
    private String host;
    @Value("#{new Integer('${gateway.grpc.client.AuthServiceV1Grpc.port}')}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        authServiceV1BlockingStub = AuthServiceV1Grpc.newBlockingStub(managedChannel);
    }

    public GAuthenticateAnyResponse authenticateAny(GAuthenticateAnyRequest request) {
        return authServiceV1BlockingStub.authenticateAny(request);
    }

    public GAuthenticateServiceResponse authenticateService(GAuthenticateServiceRequest request) {
        return authServiceV1BlockingStub.authenticateService(request);
    }

    public GGenerateTokenResponse generateToken(GGenerateTokenRequest request) {
        return authServiceV1BlockingStub.generateToken(request);
    }

    public GGetAuthenticationResponse getAuthentication(GGetAuthenticationRequest request) {
        return authServiceV1BlockingStub.getAuthentication(request);
    }

    public GAuthenticateAnyResponse updateToken(GUpdateTokenRequest request) {
        return authServiceV1BlockingStub.updateToken(request);
    }

    public Empty revokeAuthentication(GRevokeAuthenticationRequest request) {
        return authServiceV1BlockingStub.revokeAuthentication(request);
    }
}
