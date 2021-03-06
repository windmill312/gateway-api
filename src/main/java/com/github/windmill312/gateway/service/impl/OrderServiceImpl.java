package com.github.windmill312.gateway.service.impl;

import com.github.windmill312.gateway.annotation.GatewayService;
import com.github.windmill312.gateway.annotation.Logged;
import com.github.windmill312.gateway.converter.AuthConverter;
import com.github.windmill312.gateway.converter.CommonConverter;
import com.github.windmill312.gateway.converter.OrderInfoConverter;
import com.github.windmill312.gateway.grpc.client.GRpcOrderServiceClient;
import com.github.windmill312.gateway.security.InternalAuthService;
import com.github.windmill312.gateway.service.OrderService;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddOrderRequest;
import com.github.windmill312.gateway.web.to.in.UpdateOrderRequest;
import com.github.windmill312.gateway.web.to.in.UpdateOrderStatusRequest;
import com.github.windmill312.gateway.web.to.out.OrderInfo;
import com.github.windmill312.order.grpc.model.v1.GAddOrderRequest;
import com.github.windmill312.order.grpc.model.v1.GGetAllOrdersByCustomerRequest;
import com.github.windmill312.order.grpc.model.v1.GGetAllOrdersRequest;
import com.github.windmill312.order.grpc.model.v1.GGetAllOrdersResponse;
import com.github.windmill312.order.grpc.model.v1.GGetOrderRequest;
import com.github.windmill312.order.grpc.model.v1.GRemoveAllOrdersByCustomerRequest;
import com.github.windmill312.order.grpc.model.v1.GRemoveOrderRequest;
import com.github.windmill312.order.grpc.model.v1.GUpdateOrderRequest;
import com.github.windmill312.order.grpc.model.v1.GUpdateOrderStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.windmill312.gateway.exception.model.Service.ORDER;

@GatewayService(serviceName = ORDER)
public class OrderServiceImpl implements OrderService {

    private final GRpcOrderServiceClient rpcOrderServiceClient;
    private final InternalAuthService internalAuthService;

    @Autowired
    public OrderServiceImpl(
            GRpcOrderServiceClient rpcOrderServiceClient,
            InternalAuthService internalAuthService) {
        this.rpcOrderServiceClient = rpcOrderServiceClient;
        this.internalAuthService = internalAuthService;
    }

    @Logged
    @Override
    public PagedResult<OrderInfo> getAllOrders(int page, int size) {
        GGetAllOrdersResponse response = rpcOrderServiceClient.getAllOrders(
                GGetAllOrdersRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return OrderInfoConverter.convert(response.getOrdersList(), response.getPage());
    }

    @Logged
    @Override
    public List<OrderInfo> getAllOrdersByCustomer(UUID customerUid) {
        return rpcOrderServiceClient.getAllOrdersByCustomer(
                GGetAllOrdersByCustomerRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setCustomerUid(CommonConverter.convert(customerUid))
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
                                .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                                .setOrderUid(CommonConverter.convert(orderUid))
                                .build())
                        .getOrder());
    }

    @Logged
    @Override
    public UUID addOrder(AddOrderRequest request) {
        return CommonConverter.convert(
                rpcOrderServiceClient.addOrder(
                        GAddOrderRequest.newBuilder()
                                .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                                .setOrder(OrderInfoConverter.convert(request))
                                .build())
                        .getOrderUid());
    }

    @Logged
    @Override
    public void updateOrder(UUID orderUid, UpdateOrderRequest request) {
        rpcOrderServiceClient.updateOrder(
                GUpdateOrderRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setOrder(OrderInfoConverter.convert(orderUid, request))
                        .build());
    }

    @Logged
    @Override
    public void updateOrderStatus(UpdateOrderStatusRequest request) {
        rpcOrderServiceClient.updateOrderStatus(
                GUpdateOrderStatusRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setOrderUid(CommonConverter.convert(request.getOrderUid()))
                        .setStatus(OrderInfoConverter.convert(request.getStatus()))
                        .build());
    }

    @Logged
    @Override
    public void removeOrder(UUID orderUid) {
        rpcOrderServiceClient.removeOrder(
                GRemoveOrderRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setOrderUid(CommonConverter.convert(orderUid))
                        .build());
    }

    @Logged
    @Override
    public void removeAllOrdersByCustomer(UUID customerUid) {
        rpcOrderServiceClient.removeAllOrdersByCustomer(
                GRemoveAllOrdersByCustomerRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setCustomerUid(CommonConverter.convert(customerUid))
                        .build());
    }

}
