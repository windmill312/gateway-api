package com.github.windmill312.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.windmill312.gateway.web.to.in.common.OrderProducts;
import com.github.windmill312.gateway.web.to.in.common.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AddOrderRequest {

    @NotNull
    @ApiModelProperty(example = "78a8a5d0-8830-4ce4-9176-6a5d7f3dae34")
    private UUID cafeUid;

    @NotNull
    @ApiModelProperty(example = "56a8a5d0-3830-6cbd-9176-6a5d09fdae34")
    private UUID customerUid;

    @NotNull
    private Set<OrderProducts> products = new HashSet<>();

    @NotNull
    private Double totalPrice;

    @NotNull
    private Instant receiveDttm;

    @NotNull
    private OrderStatus status;

    @JsonCreator
    public AddOrderRequest(
            @JsonProperty("cafeUid") UUID cafeUid,
            @JsonProperty("customerUid") UUID customerUid,
            @JsonProperty("products") Set<OrderProducts> products,
            @JsonProperty("totalPrice") Double totalPrice,
            @JsonProperty("receiveDttm") Instant receiveDttm,
            @JsonProperty("status") OrderStatus status) {
        this.cafeUid = cafeUid;
        this.customerUid = customerUid;
        this.products = products;
        this.totalPrice = totalPrice;
        this.receiveDttm = receiveDttm;
        this.status = status;
    }

    public UUID getCafeUid() {
        return cafeUid;
    }

    public UUID getCustomerUid() {
        return customerUid;
    }

    public Set<OrderProducts> getProducts() {
        return products;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }


    public Instant getReceiveDttm() {
        return receiveDttm;
    }

    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
