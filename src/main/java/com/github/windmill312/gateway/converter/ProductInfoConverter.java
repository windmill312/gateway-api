package com.github.windmill312.gateway.converter;

import com.github.windmill312.common.grpc.model.GPage;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddProductRequest;
import com.github.windmill312.gateway.web.to.in.UpdateProductRequest;
import com.github.windmill312.gateway.web.to.out.ProductInfo;
import com.github.windmill312.gateway.web.to.out.common.ProductGroup;
import com.github.windmill312.product.grpc.model.v1.GProductInfo;

import java.util.List;
import java.util.stream.Collectors;

public class ProductInfoConverter {

    public static PagedResult<ProductInfo> convert(
            List<GProductInfo> productInfoList, GPage page) {
        return new PagedResult<>(
                page.getTotalElements(),
                productInfoList.stream()
                        .map(ProductInfoConverter::convert)
                        .collect(Collectors.toList()));
    }

    public static ProductInfo convert(GProductInfo productInfo) {
        return new ProductInfo(
                CommonConverter.convert(productInfo.getProductUid()),
                productInfo.getName(),
                productInfo.getDescription(),
                productInfo.getPrice(),
                convert(productInfo.getGroup()));
    }

    private static ProductGroup convert(GProductInfo.GProductGroup group) {
        return ProductGroup.valueOf(group.name());
    }

    private static GProductInfo.GProductGroup convert(ProductGroup group) {
        return GProductInfo.GProductGroup.valueOf(group.name());
    }

    public static GProductInfo convert(AddProductRequest request) {
        return GProductInfo.newBuilder()
                    .setGroup(convert(request.getProductGroup()))
                    .setName(request.getName())
                    .setPrice(request.getPrice())
                    .setDescription(request.getDescription())
                .build();
    }

    public static GProductInfo convert(UpdateProductRequest request) {
        return GProductInfo.newBuilder()
                    .setProductUid(CommonConverter.convert(request.getProductUid()))
                    .setGroup(convert(request.getProductGroup()))
                    .setName(request.getName())
                    .setPrice(request.getPrice())
                    .setDescription(request.getDescription())
                .build();
    }
}
