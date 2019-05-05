package com.github.windmill312.gateway.service.impl;

import com.github.windmill312.gateway.annotation.GatewayService;
import com.github.windmill312.gateway.annotation.Logged;
import com.github.windmill312.gateway.converter.AuthConverter;
import com.github.windmill312.gateway.converter.CommonConverter;
import com.github.windmill312.gateway.converter.ProductInfoConverter;
import com.github.windmill312.gateway.grpc.client.GRpcProductServiceClient;
import com.github.windmill312.gateway.security.InternalAuthService;
import com.github.windmill312.gateway.service.ProductService;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddProductRequest;
import com.github.windmill312.gateway.web.to.in.UpdateProductRequest;
import com.github.windmill312.gateway.web.to.out.ProductInfo;
import com.github.windmill312.product.grpc.model.v1.GAddProductRequest;
import com.github.windmill312.product.grpc.model.v1.GGetAllProductsRequest;
import com.github.windmill312.product.grpc.model.v1.GGetAllProductsResponse;
import com.github.windmill312.product.grpc.model.v1.GGetProductRequest;
import com.github.windmill312.product.grpc.model.v1.GLinkProductAndCafeRequest;
import com.github.windmill312.product.grpc.model.v1.GRemoveProductRequest;
import com.github.windmill312.product.grpc.model.v1.GRemoveProductsByCafeRequest;
import com.github.windmill312.product.grpc.model.v1.GUnlinkProductAndCafeRequest;
import com.github.windmill312.product.grpc.model.v1.GUpdateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.windmill312.gateway.exception.model.Service.PRODUCT;

@GatewayService(serviceName = PRODUCT)
public class ProductServiceImpl implements ProductService {

    private final GRpcProductServiceClient rpcProductServiceClient;
    private final InternalAuthService internalAuthService;

    @Autowired
    public ProductServiceImpl(
            GRpcProductServiceClient rpcProductServiceClient,
            InternalAuthService internalAuthService) {
        this.rpcProductServiceClient = rpcProductServiceClient;
        this.internalAuthService = internalAuthService;
    }

    @Logged
    @Override
    public PagedResult<ProductInfo> getAllProducts(int page, int size) {
        GGetAllProductsResponse response = rpcProductServiceClient.getAllProducts(
                GGetAllProductsRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return ProductInfoConverter.convert(response.getProductsList(), response.getPage());
    }

    @Logged
    @Override
    public List<ProductInfo> getProductsByCafe(UUID cafeUid) {
        return rpcProductServiceClient.getAllProducts(
                GGetAllProductsRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .build())
                .getProductsList()
                .stream()
                .map(ProductInfoConverter::convert)
                .collect(Collectors.toList());
    }

    @Logged
    @Override
    public ProductInfo getProductByUid(UUID productUid) {
        return ProductInfoConverter.convert(
                rpcProductServiceClient.getProduct(
                        GGetProductRequest.newBuilder()
                                .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                                .setProductUid(CommonConverter.convert(productUid))
                                .build())
                        .getProduct());
    }

    @Logged
    @Override
    public UUID addProduct(AddProductRequest request) {
        return CommonConverter.convert(
                rpcProductServiceClient.addProduct(
                        GAddProductRequest.newBuilder()
                                .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                                .setProduct(ProductInfoConverter.convert(request))
                                .build()
                )
                        .getProductUid());
    }

    @Logged
    @Override
    public void updateProduct(UpdateProductRequest request) {
        rpcProductServiceClient.updateProduct(
                GUpdateProductRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setProduct(ProductInfoConverter.convert(request))
                        .build());
    }

    @Logged
    @Override
    public void linkCafeAndProduct(UUID cafeUid, UUID productUid) {
        rpcProductServiceClient.linkProductAndCafe(
                GLinkProductAndCafeRequest.newBuilder()
                        .setProductUid(CommonConverter.convert(productUid))
                        .setCafeUid(CommonConverter.convert(cafeUid))
                        .build());
    }

    @Logged
    @Override
    public void unlinkCafeAndProduct(UUID cafeUid, UUID productUid) {
        rpcProductServiceClient.unlinkProductAndCafe(
                GUnlinkProductAndCafeRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setProductUid(CommonConverter.convert(productUid))
                        .setCafeUid(CommonConverter.convert(cafeUid))
                        .build());
    }

    @Logged
    @Override
    public void removeProduct(UUID productUid) {
        rpcProductServiceClient.removeProduct(
                GRemoveProductRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setProductUid(CommonConverter.convert(productUid))
                        .build());
    }

    @Logged
    @Override
    public void removeAllProductsByCafe(UUID cafeUid) {
        rpcProductServiceClient.removeProductsByCafe(
                GRemoveProductsByCafeRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setCafeUid(CommonConverter.convert(cafeUid))
                        .build());
    }

}
