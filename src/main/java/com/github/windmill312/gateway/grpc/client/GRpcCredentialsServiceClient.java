package com.github.windmill312.gateway.grpc.client;

import com.github.windmill312.auth.grpc.model.v1.GAddCredentialsRequest;
import com.github.windmill312.auth.grpc.model.v1.GGetPrincipalIdentifierRequest;
import com.github.windmill312.auth.grpc.model.v1.GGetPrincipalIdentifierResponse;
import com.github.windmill312.auth.grpc.model.v1.GGetPrincipalKeyResponse;
import com.github.windmill312.auth.grpc.model.v1.GGetPrincipalOuterKeyRequest;
import com.github.windmill312.auth.grpc.service.v1.CredentialsServiceV1Grpc;
import com.github.windmill312.common.grpc.model.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GRpcCredentialsServiceClient {

    private CredentialsServiceV1Grpc.CredentialsServiceV1BlockingStub credentialsServiceV1BlockingStub;

    @Value("#{new String('${gateway.grpc.client.CredentialsServiceV1Grpc.host}')}")
    private String host;
    @Value("#{new Integer('${gateway.grpc.client.CredentialsServiceV1Grpc.port}')}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        credentialsServiceV1BlockingStub = CredentialsServiceV1Grpc.newBlockingStub(managedChannel);
    }

    public GGetPrincipalKeyResponse getPrincipalOuterKey(GGetPrincipalOuterKeyRequest request) {
        return credentialsServiceV1BlockingStub.getPrincipalKey(request);
    }

    public Empty addCredentials(GAddCredentialsRequest request) {
        return credentialsServiceV1BlockingStub.addCredentials(request);
    }

    public GGetPrincipalIdentifierResponse getIdentifier(GGetPrincipalIdentifierRequest request) {
        return credentialsServiceV1BlockingStub.getPrincipalIdentifier(request);
    }
}
