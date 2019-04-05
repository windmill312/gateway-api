package com.sychev.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class LinkProductAndCafeRequest {

    @NotNull
    @ApiModelProperty(example = "a9cda316-ceb5-4d15-abd6-5d3d40f73d75")
    private UUID cafeUid;

    @NotNull
    @ApiModelProperty(example = "a9cda316-ceb5-4d15-abd6-5d3d40f73d75")
    private UUID productUid;

    @JsonCreator
    public LinkProductAndCafeRequest(
            @JsonProperty("cafeUid") UUID cafeUid,
            @JsonProperty("productUid") UUID productUid) {
        this.cafeUid = cafeUid;
        this.productUid = productUid;
    }

    public UUID getCafeUid() {
        return cafeUid;
    }

    public UUID getProductUid() {
        return productUid;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
