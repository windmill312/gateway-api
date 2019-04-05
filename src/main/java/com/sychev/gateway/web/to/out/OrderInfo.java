package com.sychev.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.sychev.gateway.web.to.in.common.OrderProducts;
import com.sychev.gateway.web.to.in.common.OrderStatus;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class OrderInfo {

    private UUID orderUid = UUID.randomUUID();
    private UUID cafeUid;
    private UUID customerUid;
    private Set<OrderProducts> products = new HashSet<>();
    private Double totalPrice;
    private Instant createDttm;
    private Instant receiveDttm;
    private OrderStatus status;

    public OrderInfo(UUID orderUid,
                     UUID cafeUid,
                     UUID customerUid,
                     Set<OrderProducts> products,
                     Double totalPrice,
                     Instant createDttm,
                     Instant receiveDttm,
                     OrderStatus status) {
        this.orderUid = orderUid;
        this.cafeUid = cafeUid;
        this.customerUid = customerUid;
        this.products = products;
        this.totalPrice = totalPrice;
        this.createDttm = createDttm;
        this.receiveDttm = receiveDttm;
        this.status = status;
    }

    @JsonGetter("orderUid")
    public UUID getOrderUid() {
        return orderUid;
    }

    @JsonGetter("cafeUid")
    public UUID getCafeUid() {
        return cafeUid;
    }

    @JsonGetter("customerUid")
    public UUID getCustomerUid() {
        return customerUid;
    }

    @JsonGetter("products")
    public Set<OrderProducts> getProducts() {
        return products;
    }

    @JsonGetter("productUid")
    public Double getTotalPrice() {
        return totalPrice;
    }

    @JsonGetter("createDttm")
    public Instant getCreateDttm() {
        return createDttm;
    }

    @JsonGetter("receiveDttm")
    public Instant getReceiveDttm() {
        return receiveDttm;
    }

    @JsonGetter("status")
    public OrderStatus getStatus() {
        return status;
    }

}
