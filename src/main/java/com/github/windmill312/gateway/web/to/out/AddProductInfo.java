package com.github.windmill312.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.UUID;

public class AddProductInfo {

    private UUID productUid;

    public AddProductInfo(UUID productUid) {
        this.productUid = productUid;
    }

    @JsonGetter("productUid")
    public UUID getProductUid() {
        return productUid;
    }
}
