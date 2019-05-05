package com.github.windmill312.gateway.service;

import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddProductRequest;
import com.github.windmill312.gateway.web.to.in.UpdateProductRequest;
import com.github.windmill312.gateway.web.to.out.ProductInfo;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    PagedResult<ProductInfo> getAllProducts(int page, int size);

    PagedResult<ProductInfo> getProductsByCafe(UUID cafeUid, int page, int size);

    ProductInfo getProductByUid(UUID productUid);

    UUID addProduct(AddProductRequest entity);

    void updateProduct(UpdateProductRequest entity);

    void linkCafeAndProduct(UUID cafeUid, UUID productUid);

    void unlinkCafeAndProduct(UUID cafeUid, UUID productUid);

    void removeProduct(UUID productUid);

    void removeAllProductsByCafe(UUID cafeUid);
}
