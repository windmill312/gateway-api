package com.github.windmill312.gateway.service.impl;

import com.github.windmill312.coffeehouse.grpc.model.v1.*;
import com.github.windmill312.gateway.annotation.GatewayService;
import com.github.windmill312.gateway.annotation.Logged;
import com.github.windmill312.gateway.converter.AuthConverter;
import com.github.windmill312.gateway.converter.CoffeeHouseInfoConverter;
import com.github.windmill312.gateway.converter.CommonConverter;
import com.github.windmill312.gateway.grpc.client.GRpcCoffeeHouseServiceClient;
import com.github.windmill312.gateway.security.InternalAuthService;
import com.github.windmill312.gateway.service.CoffeeHouseService;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddCoffeeHouseRequest;
import com.github.windmill312.gateway.web.to.in.UpdateCoffeeHouseRequest;
import com.github.windmill312.gateway.web.to.out.CoffeeHouseInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static com.github.windmill312.gateway.exception.model.Service.COFFEEHOUSE;

@GatewayService(serviceName = COFFEEHOUSE)
public class CoffeeHouseServiceImpl implements CoffeeHouseService {

    private final GRpcCoffeeHouseServiceClient rpcCoffeeHouseServiceClient;
    private final InternalAuthService internalAuthService;

    @Autowired
    public CoffeeHouseServiceImpl(
            GRpcCoffeeHouseServiceClient rpcCoffeeHouseServiceClient,
            InternalAuthService internalAuthService) {
        this.rpcCoffeeHouseServiceClient = rpcCoffeeHouseServiceClient;
        this.internalAuthService = internalAuthService;
    }

    @Logged
    @Override
    public PagedResult<CoffeeHouseInfo> getCafes(Double latitude, Double longitude, int page, int size) {

        if (Optional.ofNullable(latitude).isPresent() && Optional.ofNullable(longitude).isPresent())
            return getAllCafesByLongitudeAndLatitudeInternal(latitude, longitude, page, size);
        else
            return getAllCafesInternal(page, size);
    }

    private PagedResult<CoffeeHouseInfo> getAllCafesInternal(int page, int size) {

        GGetAllCafesResponse response = rpcCoffeeHouseServiceClient.getAllCafes(
                GGetAllCafesRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return CoffeeHouseInfoConverter.convert(response.getCafesList(), response.getPage());

    }

    private PagedResult<CoffeeHouseInfo> getAllCafesByLongitudeAndLatitudeInternal(Double latitude, Double longitude, int page, int size) {
        GGetAllCafesAroundClientResponse response = rpcCoffeeHouseServiceClient.getAllCafesAroundClient(
                GGetAllCafesAroundClientRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setLocation(CommonConverter.convert(latitude, longitude))
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return CoffeeHouseInfoConverter.convert(response.getCafesList(), response.getPage());
    }

    @Logged
    @Override
    public CoffeeHouseInfo getCafeByUid(UUID cafeUid) {
        return CoffeeHouseInfoConverter.convert(
                rpcCoffeeHouseServiceClient.getCafe(
                        GGetCafeRequest.newBuilder()
                                .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                                .setCafeUid(CommonConverter.convert(cafeUid))
                                .build())
                        .getCafe());
    }

    @Logged
    @Override
    public UUID addCafe(AddCoffeeHouseRequest request) {
        return CommonConverter.convert(
                rpcCoffeeHouseServiceClient.addCafe(
                        GAddCafeRequest.newBuilder()
                                .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                                .setCafe(CoffeeHouseInfoConverter.convert(request))
                                .build())
                        .getCafeUid());
    }

    @Logged
    @Override
    public void updateCafe(UpdateCoffeeHouseRequest request) {
        rpcCoffeeHouseServiceClient.updateCafe(
                GUpdateCafeRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setCafe(CoffeeHouseInfoConverter.convert(request))
                        .build());
    }

    @Logged
    @Override
    public void removeCafe(UUID cafeUid) {
        rpcCoffeeHouseServiceClient.removeCafe(
                GRemoveCafeRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setCafeUid(CommonConverter.convert(cafeUid))
                        .build());
    }
}
