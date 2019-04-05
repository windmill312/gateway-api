package com.sychev.gateway.web.to.in.common;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class OrderProducts {

    @NotNull
    private UUID productUid;

    @NotNull
    private Integer quantity;

    public OrderProducts(@NotNull UUID productUid, @NotNull Integer quantity) {
        this.productUid = productUid;
        this.quantity = quantity;
    }

    public OrderProducts() {
    }

    public UUID getProductUid() {
        return productUid;
    }

    public OrderProducts setProductUid(UUID productUid) {
        this.productUid = productUid;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderProducts setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
