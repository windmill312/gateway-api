package com.github.windmill312.gateway.web.controller;

import com.github.windmill312.gateway.service.OrderService;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddOrderRequest;
import com.github.windmill312.gateway.web.to.in.UpdateOrderRequest;
import com.github.windmill312.gateway.web.to.out.AddOrderInfo;
import com.github.windmill312.gateway.web.to.out.OrderInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<PagedResult<OrderInfo>> getOrders(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        logger.debug("Getting all orders for page: {} and page size: {}", page, size);

        return ResponseEntity.ok(orderService.getAllOrders(page, size));

    }

    @GetMapping(path = "/customer/{uuid}")
    public ResponseEntity<List<OrderInfo>> getOrdersByCustomer(@PathVariable UUID uuid) {
        logger.debug("Getting all orders by customer: {}", uuid);

        return ResponseEntity.ok(orderService.getAllOrdersByCustomer(uuid));
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<OrderInfo> getOrderInfo(@PathVariable UUID uuid) {
        logger.debug("Getting order info by uid: {}", uuid);

        return ResponseEntity.ok(orderService.getOrderByUid(uuid));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddOrderInfo> addOrder(@RequestBody @Valid AddOrderRequest request) {
        logger.debug("Adding order with customer uid: {} and cafe uid: {}", request.getCustomerUid(), request.getCafeUid());

        return ResponseEntity.ok(new AddOrderInfo(orderService.addOrder(request)));
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> updateOrder(@RequestBody @Valid UpdateOrderRequest request) {
        logger.debug("Updating order with uid: {}", request.getOrderUid());

        orderService.updateOrder(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{uuid}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID uuid) {
        logger.debug("Deleting order with uid: {}", uuid);

        orderService.removeOrder(uuid);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/customer/{uuid}")
    public ResponseEntity<Void> deleteOrdersByCustomer(@PathVariable UUID uuid) {
        logger.debug("Deleting orders by customer with uid: {}", uuid);

        orderService.removeAllOrdersByCustomer(uuid);
        return ResponseEntity.noContent().build();
    }

}
