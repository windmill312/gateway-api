package com.github.windmill312.gateway.service.impl;

import com.github.windmill312.order.grpc.model.v1.*;
import com.github.windmill312.gateway.annotation.GatewayService;
import com.github.windmill312.gateway.annotation.Logged;
import com.github.windmill312.gateway.converter.CommonConverter;
import com.github.windmill312.gateway.converter.OrderInfoConverter;
import com.github.windmill312.gateway.grpc.client.GRpcOrderServiceClient;
import com.github.windmill312.gateway.service.OrderService;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddOrderRequest;
import com.github.windmill312.gateway.web.to.in.UpdateOrderRequest;
import com.github.windmill312.gateway.web.to.out.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.windmill312.gateway.exception.model.Service.ORDER;

@GatewayService(serviceName = ORDER)
public class OrderServiceImpl implements OrderService {

    private final GRpcOrderServiceClient rpcOrderServiceClient;

    @Autowired
    public OrderServiceImpl(GRpcOrderServiceClient rpcOrderServiceClient) {
        this.rpcOrderServiceClient = rpcOrderServiceClient;
    }

    @Logged
    @Override
    public PagedResult<OrderInfo> getAllOrders(int page, int size) {
        GGetAllOrdersResponse response = rpcOrderServiceClient.getAllOrders(
                GGetAllOrdersRequest.newBuilder()
                        //.setAuthentication(internalAuthService.getGAuthentication())
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return OrderInfoConverter.convert(response.getOrdersList(), response.getPage());
    }

    @Logged
    @Override
    public List<OrderInfo> getAllOrdersByCustomer(UUID customerUid) {
        return rpcOrderServiceClient.getAllOrdersByCustomer(
                GGetAllOrdersByCustomerRequest.newBuilder()
                        //.setAuthentication(internalAuthService.getGAuthentication())
                        .build())
                .getOrdersList()
                .stream()
                .map(OrderInfoConverter::convert)
                .collect(Collectors.toList());
    }

    @Logged
    @Override
    public OrderInfo getOrderByUid(UUID orderUid) {
        return OrderInfoConverter.convert(
                rpcOrderServiceClient.getOrder(
                        GGetOrderRequest.newBuilder()
                                //.setAuthentication(internalAuthService.getGAuthentication())
                                .setOrderUid(CommonConverter.convert(orderUid))
                                .build())
                        .getOrder());
    }

    @Logged
    @Override
    public UUID addOrder(AddOrderRequest request) {
        return CommonConverter.convert(
                rpcOrderServiceClient.addOrder(
                        OrderInfoConverter.convert(request))
                        .getOrderUid());
    }

    @Logged
    @Override
    public void updateOrder(UpdateOrderRequest request) {
        rpcOrderServiceClient.updateOrder(
                OrderInfoConverter.convert(request));
    }

    @Logged
    @Override
    public void removeOrder(UUID orderUid) {
        rpcOrderServiceClient.removeOrder(
                GRemoveOrderRequest.newBuilder()
                        .setOrderUid(CommonConverter.convert(orderUid))
                        .build());
    }

    @Logged
    @Override
    public void removeAllOrdersByCustomer(UUID customerUid) {
        rpcOrderServiceClient.removeAllOrdersByCustomer(
                GRemoveAllOrdersByCustomerRequest.newBuilder()
                        .setCustomerUid(CommonConverter.convert(customerUid))
                        .build());
    }

}
