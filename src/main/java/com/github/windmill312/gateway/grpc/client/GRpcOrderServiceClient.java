package com.github.windmill312.gateway.grpc.client;

import com.github.windmill312.common.grpc.model.Empty;
import com.github.windmill312.order.grpc.model.v1.GAddOrderRequest;
import com.github.windmill312.order.grpc.model.v1.GAddOrderResponse;
import com.github.windmill312.order.grpc.model.v1.GGetAllOrdersByCustomerRequest;
import com.github.windmill312.order.grpc.model.v1.GGetAllOrdersByCustomerResponse;
import com.github.windmill312.order.grpc.model.v1.GGetAllOrdersRequest;
import com.github.windmill312.order.grpc.model.v1.GGetAllOrdersResponse;
import com.github.windmill312.order.grpc.model.v1.GGetOrderRequest;
import com.github.windmill312.order.grpc.model.v1.GGetOrderResponse;
import com.github.windmill312.order.grpc.model.v1.GRemoveAllOrdersByCustomerRequest;
import com.github.windmill312.order.grpc.model.v1.GRemoveOrderRequest;
import com.github.windmill312.order.grpc.model.v1.GUpdateOrderRequest;
import com.github.windmill312.order.grpc.service.v1.OrderServiceV1Grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GRpcOrderServiceClient {

    private OrderServiceV1Grpc.OrderServiceV1BlockingStub orderServiceV1BlockingStub;

    @Value("#{new String('${gateway.grpc.client.OrderServiceV1Grpc.host}')}")
    private String host;
    @Value("#{new Integer('${gateway.grpc.client.OrderServiceV1Grpc.port}')}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        orderServiceV1BlockingStub = OrderServiceV1Grpc.newBlockingStub(managedChannel);
    }

    public GGetOrderResponse getOrder(GGetOrderRequest request) {
        return orderServiceV1BlockingStub.getOrder(request);
    }

    public GGetAllOrdersResponse getAllOrders(GGetAllOrdersRequest request) {
        return orderServiceV1BlockingStub.getAllOrders(request);
    }

    public GGetAllOrdersByCustomerResponse getAllOrdersByCustomer(GGetAllOrdersByCustomerRequest request) {
        return orderServiceV1BlockingStub.getAllOrdersByCustomer(request);
    }

    public GAddOrderResponse addOrder(GAddOrderRequest request) {
        return orderServiceV1BlockingStub.addOrder(request);
    }

    public Empty updateOrder(GUpdateOrderRequest request) {
        return orderServiceV1BlockingStub.updateOrder(request);
    }

    public Empty removeOrder(GRemoveOrderRequest request) {
        return orderServiceV1BlockingStub.removeOrder(request);
    }

    public Empty removeAllOrdersByCustomer(GRemoveAllOrdersByCustomerRequest request) {
        return orderServiceV1BlockingStub.removeAllOrdersByCustomer(request);
    }


}
