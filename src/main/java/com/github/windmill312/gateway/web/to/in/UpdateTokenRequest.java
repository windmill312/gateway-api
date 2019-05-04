package com.github.windmill312.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.windmill312.gateway.web.to.out.common.ProductGroup;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UpdateTokenRequest {

    @NotNull
    @ApiModelProperty(example = "a9cda316-ceb5-4d15-abd6-5d3d40f73d75")
    private UUID customerUid;

    @NotBlank
    @ApiModelProperty(example = "a9cda316-ceb5-4d15-abd6-5d3d40f73d75a9cda316-ceb5-4d15-abd6-5d3d40f73d75")
    private String refreshToken;

    @JsonCreator
    public UpdateTokenRequest(
            @JsonProperty("customerUid") UUID customerUid,
            @JsonProperty("refreshToken") String refreshToken) {
        this.customerUid = customerUid;
        this.refreshToken = refreshToken;
    }

    public UUID getCustomerUid() {
        return customerUid;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
