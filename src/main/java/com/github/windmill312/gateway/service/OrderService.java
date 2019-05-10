package com.github.windmill312.gateway.service;

import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddOrderRequest;
import com.github.windmill312.gateway.web.to.in.UpdateOrderRequest;
import com.github.windmill312.gateway.web.to.in.UpdateOrderStatusRequest;
import com.github.windmill312.gateway.web.to.out.OrderInfo;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    PagedResult<OrderInfo> getAllOrders(int page, int size);

    List<OrderInfo> getAllOrdersByCustomer(UUID customerUid);

    OrderInfo getOrderByUid(UUID orderUid);

    UUID addOrder(AddOrderRequest request);

    void updateOrder(UUID orderUid, UpdateOrderRequest request);

    void removeOrder(UUID orderUid);

    void removeAllOrdersByCustomer(UUID customerUid);

    void updateOrderStatus(UpdateOrderStatusRequest request);
}
