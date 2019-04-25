package com.github.windmill312.gateway.grpc.client;

import com.github.windmill312.auth.grpc.model.v1.GAddPrincipalRequest;
import com.github.windmill312.auth.grpc.model.v1.GAddPrincipalResponse;
import com.github.windmill312.auth.grpc.model.v1.GDeletePrincipalRequest;
import com.github.windmill312.auth.grpc.service.v1.PrincipalServiceV1Grpc;
import com.github.windmill312.common.grpc.model.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GRpcPrincipalServiceClient {

    private PrincipalServiceV1Grpc.PrincipalServiceV1BlockingStub principalServiceV1BlockingStub;

    @Value("#{new String('${gateway.grpc.client.PrincipalServiceV1Grpc.host}')}")
    private String host;
    @Value("#{new Integer('${gateway.grpc.client.PrincipalServiceV1Grpc.port}')}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        principalServiceV1BlockingStub = PrincipalServiceV1Grpc.newBlockingStub(managedChannel);
    }

    public GAddPrincipalResponse addPrincipal(GAddPrincipalRequest request) {
        return principalServiceV1BlockingStub.addPrincipal(request);
    }

    public Empty deletePrincipal(GDeletePrincipalRequest request) {
        return principalServiceV1BlockingStub.deletePrincipal(request);
    }
}
