package com.github.windmill312.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class AddCoffeeHouseInfo {

    private UUID cafeUid;

    public AddCoffeeHouseInfo(UUID cafeUid) {
        this.cafeUid = cafeUid;
    }

    @JsonGetter("cafeUid")
    public UUID getCafeUid() {
        return cafeUid;
    }
}
