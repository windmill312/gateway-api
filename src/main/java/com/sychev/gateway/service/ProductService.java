package com.sychev.gateway.service;

import com.sychev.gateway.web.to.common.PagedResult;
import com.sychev.gateway.web.to.in.AddProductRequest;
import com.sychev.gateway.web.to.in.UpdateProductRequest;
import com.sychev.gateway.web.to.out.ProductInfo;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    PagedResult<ProductInfo> getAllProducts(int page, int size);

    List<ProductInfo> getProductsByCafe(UUID cafeUid);

    ProductInfo getProductByUid(UUID productUid);

    UUID addProduct(AddProductRequest entity);

    void updateProduct(UpdateProductRequest entity);

    void linkCafeAndProduct(UUID cafeUid, UUID productUid);

    void unlinkCafeAndProduct(UUID cafeUid, UUID productUid);

    void removeProduct(UUID productUid);

    void removeAllProductsByCafe(UUID cafeUid);
}
