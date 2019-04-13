package com.github.windmill312.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.windmill312.gateway.web.to.out.common.ProductGroup;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UpdateProductRequest {

    @NotNull
    @ApiModelProperty(example = "a9cda316-ceb5-4d15-abd6-5d3d40f73d75")
    private UUID productUid;

    @NotBlank
    @ApiModelProperty(example = "Product Name")
    private String name;

    @NotBlank
    @ApiModelProperty(example = "Product Description")
    private String description;

    @NotNull
    private Integer price;

    @NotNull
    private ProductGroup productGroup;

    @JsonCreator
    public UpdateProductRequest(
            @JsonProperty("productUid") UUID productUid,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("price") Integer price,
            @JsonProperty("productGroup") ProductGroup productGroup) {
        this.productUid = productUid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productGroup = productGroup;
    }

    public UUID getProductUid() {
        return productUid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
