package com.sychev.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class CoffeeHouseInfo {

    private UUID uidCafe = UUID.randomUUID();
    private Double latitude;
    private Double longitude;
    private String name;
    private String description;
    private UUID ownerUid;

    public CoffeeHouseInfo(UUID uidCafe,
                           Double latitude,
                           Double longitude,
                           String name,
                           String description,
                           UUID ownerUid) {
        this.uidCafe = uidCafe;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
        this.ownerUid = ownerUid;
    }

    @JsonGetter("uidCafe")
    public UUID getUidCafe() {
        return uidCafe;
    }

    @JsonGetter("latitude")
    public Double getLatitude() {
        return latitude;
    }

    @JsonGetter("longitude")
    public Double getLongitude() {
        return longitude;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonGetter("description")
    public String getDescription() {
        return description;
    }

    @JsonGetter("ownerUid")
    public UUID getOwnerUid() {
        return ownerUid;
    }

}
