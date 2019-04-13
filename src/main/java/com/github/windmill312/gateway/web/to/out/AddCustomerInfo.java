package com.github.windmill312.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class AddCustomerInfo {

    private UUID customerUid;

    public AddCustomerInfo(UUID customerUid) {
        this.customerUid = customerUid;
    }

    @JsonGetter("customerUid")
    public UUID getCustomerUid() {
        return customerUid;
    }
}
