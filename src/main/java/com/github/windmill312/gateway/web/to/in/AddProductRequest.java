package com.github.windmill312.gateway.web.to.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.windmill312.gateway.web.to.out.common.ProductGroup;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddProductRequest {

    @NotBlank
    @ApiModelProperty(example = "Product Name")
    private String name;

    @NotBlank
    @ApiModelProperty(example = "Product Description")
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private ProductGroup productGroup;

    @JsonCreator
    public AddProductRequest(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("price") Double price,
            @JsonProperty("productGroup") ProductGroup productGroup) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productGroup = productGroup;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
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
