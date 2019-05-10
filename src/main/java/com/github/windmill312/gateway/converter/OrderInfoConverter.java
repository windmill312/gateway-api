package com.github.windmill312.gateway.converter;

import com.github.windmill312.common.grpc.model.GPage;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddOrderRequest;
import com.github.windmill312.gateway.web.to.in.UpdateOrderRequest;
import com.github.windmill312.gateway.web.to.in.common.OrderProducts;
import com.github.windmill312.gateway.web.to.in.common.OrderStatus;
import com.github.windmill312.gateway.web.to.out.OrderInfo;
import com.github.windmill312.order.grpc.model.v1.GOrderInfo;
import com.github.windmill312.order.grpc.model.v1.GOrderStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderInfoConverter {

    public static PagedResult<OrderInfo> convert(
            List<GOrderInfo> OrderInfoList, GPage page) {
        return new PagedResult<>(
                page.getTotalElements(),
                OrderInfoList.stream()
                        .map(OrderInfoConverter::convert)
                        .collect(Collectors.toList()));
    }

    public static OrderInfo convert(GOrderInfo orderInfo) {
        return new OrderInfo(
                CommonConverter.convert(orderInfo.getOrderUid()),
                CommonConverter.convert(orderInfo.getCafeUid()),
                CommonConverter.convert(orderInfo.getCustomerUid()),
                orderInfo.getProductsList().stream()
                        .map(OrderInfoConverter::convert)
                        .collect(Collectors.toSet()),
                orderInfo.getPrice(),
                Instant.ofEpochMilli(orderInfo.getCreateMills()),
                Instant.ofEpochMilli(orderInfo.getReceiveMills()),
                convert(orderInfo.getStatus()));
    }

    public static OrderStatus convert(GOrderStatus status) {
        return OrderStatus.valueOf(status.name());
    }

    public static GOrderStatus convert(OrderStatus status) {
        return GOrderStatus.valueOf(status.name());
    }

    private static OrderProducts convert(GOrderInfo.OrderProduct c) {
        return new OrderProducts()
                .setProductUid(CommonConverter.convert(c.getProductUid()))
                .setQuantity(c.getQuantity());
    }

    private static GOrderInfo.OrderProduct convert(OrderProducts c) {
        return GOrderInfo.OrderProduct.newBuilder()
                .setProductUid(CommonConverter.convert(c.getProductUid()))
                .setQuantity(c.getQuantity())
                .build();
    }


    public static GOrderInfo convert(AddOrderRequest request) {
        return GOrderInfo.newBuilder()
                .setStatus(convert(request.getStatus()))
                .setCafeUid(CommonConverter.convert(request.getCafeUid()))
                .setCustomerUid(CommonConverter.convert(request.getCustomerUid()))
                .addAllProducts(request.getProducts().stream()
                        .map(OrderInfoConverter::convert)
                        .collect(Collectors.toSet()))
                .setPrice(request.getTotalPrice())
                .setReceiveMills(request.getReceiveDttm().toEpochMilli())
                .build();
    }

    public static GOrderInfo convert(UUID orderUid, UpdateOrderRequest request) {
        return GOrderInfo.newBuilder()
                .setOrderUid(CommonConverter.convert(orderUid))
                .setStatus(convert(request.getStatus()))
                .setCafeUid(CommonConverter.convert(request.getCafeUid()))
                .setCustomerUid(CommonConverter.convert(request.getCustomerUid()))
                .addAllProducts(request.getProducts().stream()
                        .map(OrderInfoConverter::convert)
                        .collect(Collectors.toSet()))
                .setPrice(request.getTotalPrice())
                .setReceiveMills(request.getReceiveDttm().toEpochMilli())
                .build();
    }
}
