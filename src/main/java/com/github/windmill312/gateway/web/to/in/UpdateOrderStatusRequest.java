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

public class UpdateOrderStatusRequest {

    @NotNull
    @ApiModelProperty(example = "78a8a5d0-8830-4ce4-9176-6a5d7f3dae34")
    private UUID orderUid;

    @NotNull
    private OrderStatus status;

    @JsonCreator
    public UpdateOrderStatusRequest(
            @JsonProperty("orderUid") UUID orderUid,
            @JsonProperty("status") OrderStatus status) {
        this.orderUid = orderUid;
        this.status = status;
    }

    public UUID getOrderUid() {
        return orderUid;
    }

    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

