package com.github.windmill312.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class IdentifierRequest {

    @NotNull
    @ApiModelProperty(example = "a9cda316-ceb5-4d15-abd6-5d3d40f73d75")
    private UUID principalUid;

    @JsonCreator
    public IdentifierRequest(
            @JsonProperty("principalUid") UUID principalUid) {
        this.principalUid = principalUid;
    }

    public UUID getPrincipalUid() {
        return principalUid;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
