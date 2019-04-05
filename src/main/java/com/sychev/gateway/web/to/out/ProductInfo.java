package com.sychev.gateway.web.to.out;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.sychev.gateway.web.to.out.common.ProductGroup;

import java.util.UUID;

public class ProductInfo {

    private UUID productUid = UUID.randomUUID();
    private String name;
    private String description;
    private Integer price;
    private ProductGroup productGroup;

    public ProductInfo(
            UUID productUid,
            String name,
            String description,
            Integer price,
            ProductGroup productGroup) {
        this.productUid = productUid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productGroup = productGroup;
    }

    @JsonGetter("productUid")
    public UUID getProductUid() {
        return productUid;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonGetter("description")
    public String getDescription() {
        return description;
    }

    @JsonGetter("price")
    public Integer getPrice() {
        return price;
    }

    @JsonGetter("productGroup")
    public ProductGroup getProductGroup() {
        return productGroup;
    }
}
