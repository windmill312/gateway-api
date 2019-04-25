package com.github.windmill312.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class CustomerInfo {

    private UUID userUid;

    public CustomerInfo(UUID userUid) {
        this.userUid = userUid;
    }

    @JsonGetter("userUid")
    public UUID getUserUid() {
        return userUid;
    }
}
