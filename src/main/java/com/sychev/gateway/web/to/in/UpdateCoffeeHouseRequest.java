package com.sychev.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UpdateCoffeeHouseRequest {

    @NotNull
    @ApiModelProperty(example = "a9cda316-ceb5-4d15-abd6-5d3d40f73d75")
    private UUID cafeUid;

    @NotNull
    @ApiModelProperty(example = "CoffeeHouse Latitude")
    private Double latitude;

    @NotNull
    @ApiModelProperty(example = "CoffeeHouse Longitude")
    private Double longitude;

    @NotBlank
    @ApiModelProperty(example = "CoffeeHouse Name")
    private String name;

    @NotBlank
    @ApiModelProperty(example = "CoffeeHouse Description")
    private String description;

    @NotNull
    @ApiModelProperty(example = "a9cda316-ceb5-4d15-abd6-5d3d40f73d75")
    private UUID ownerUid;

    @JsonCreator
    public UpdateCoffeeHouseRequest(
            @JsonProperty("cafeUid") UUID cafeUid,
            @JsonProperty("latitude") Double latitude,
            @JsonProperty("longitude") Double longitude,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("ownerUid") UUID ownerUid) {
        this.cafeUid = cafeUid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
        this.ownerUid = ownerUid;
    }

    public UUID getCafeUid() {
        return cafeUid;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UUID getOwnerUid() {
        return ownerUid;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
