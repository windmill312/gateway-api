package com.github.windmill312.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class AddOrderInfo {

    private UUID orderUid;

    public AddOrderInfo(UUID orderUid) {
        this.orderUid = orderUid;
    }

    @JsonGetter("orderUid")
    public UUID getOrderUid() {
        return orderUid;
    }
}
