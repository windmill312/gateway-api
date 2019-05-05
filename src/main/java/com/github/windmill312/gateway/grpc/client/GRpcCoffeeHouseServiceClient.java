package com.github.windmill312.gateway.grpc.client;

import com.github.windmill312.coffeehouse.grpc.model.v1.GAddCafeRequest;
import com.github.windmill312.coffeehouse.grpc.model.v1.GAddCafeResponse;
import com.github.windmill312.coffeehouse.grpc.model.v1.GGetAllCafesAroundClientRequest;
import com.github.windmill312.coffeehouse.grpc.model.v1.GGetAllCafesAroundClientResponse;
import com.github.windmill312.coffeehouse.grpc.model.v1.GGetAllCafesRequest;
import com.github.windmill312.coffeehouse.grpc.model.v1.GGetAllCafesResponse;
import com.github.windmill312.coffeehouse.grpc.model.v1.GGetCafeRequest;
import com.github.windmill312.coffeehouse.grpc.model.v1.GGetCafeResponse;
import com.github.windmill312.coffeehouse.grpc.model.v1.GRemoveCafeRequest;
import com.github.windmill312.coffeehouse.grpc.model.v1.GUpdateCafeRequest;
import com.github.windmill312.coffeehouse.grpc.service.v1.CoffeeHouseServiceV1Grpc;
import com.github.windmill312.common.grpc.model.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GRpcCoffeeHouseServiceClient {

    private CoffeeHouseServiceV1Grpc.CoffeeHouseServiceV1BlockingStub coffeeHouseServiceV1BlockingStub;

    @Value("#{new String('${gateway.grpc.client.CoffeeHouseServiceV1Grpc.host}')}")
    private String host;
    @Value("#{new Integer('${gateway.grpc.client.CoffeeHouseServiceV1Grpc.port}')}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        coffeeHouseServiceV1BlockingStub = CoffeeHouseServiceV1Grpc.newBlockingStub(managedChannel);
    }

    public GGetCafeResponse getCafe(GGetCafeRequest request) {
        return coffeeHouseServiceV1BlockingStub.getCafe(request);
    }

    public GGetAllCafesResponse getAllCafes(GGetAllCafesRequest request) {
        return coffeeHouseServiceV1BlockingStub.getAllCafes(request);
    }

    public GGetAllCafesAroundClientResponse getAllCafesAroundClient(GGetAllCafesAroundClientRequest request) {
        return coffeeHouseServiceV1BlockingStub.getAllCafesAroundClient(request);
    }

    public GAddCafeResponse addCafe(GAddCafeRequest request) {
        return coffeeHouseServiceV1BlockingStub.addCafe(request);
    }

    public Empty updateCafe(GUpdateCafeRequest request) {
        return coffeeHouseServiceV1BlockingStub.updateCafe(request);
    }

    public Empty removeCafe(GRemoveCafeRequest request) {
        return coffeeHouseServiceV1BlockingStub.removeCafe(request);
    }
}
