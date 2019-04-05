package com.sychev.gateway.service.impl;

import com.sychev.coffeehouse.grpc.model.v1.*;
import com.sychev.gateway.annotation.GatewayService;
import com.sychev.gateway.annotation.Logged;
import com.sychev.gateway.converter.CoffeeHouseInfoConverter;
import com.sychev.gateway.converter.CommonConverter;
import com.sychev.gateway.grpc.client.GRpcCoffeeHouseServiceClient;
import com.sychev.gateway.service.CoffeeHouseService;
import com.sychev.gateway.web.to.common.PagedResult;
import com.sychev.gateway.web.to.in.AddCoffeeHouseRequest;
import com.sychev.gateway.web.to.in.UpdateCoffeeHouseRequest;
import com.sychev.gateway.web.to.out.CoffeeHouseInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static com.sychev.gateway.exception.model.Service.COFFEEHOUSE;

@GatewayService(serviceName = COFFEEHOUSE)
public class CoffeeHouseServiceImpl implements CoffeeHouseService {

    private final GRpcCoffeeHouseServiceClient rpcCoffeeHouseServiceClient;

    @Autowired
    public CoffeeHouseServiceImpl(GRpcCoffeeHouseServiceClient rpcCoffeeHouseServiceClient) {
        this.rpcCoffeeHouseServiceClient = rpcCoffeeHouseServiceClient;
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
                        //.setAuthentication(internalAuthService.getGAuthentication())
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return CoffeeHouseInfoConverter.convert(response.getCafesList(), response.getPage());

    }

    private PagedResult<CoffeeHouseInfo> getAllCafesByLongitudeAndLatitudeInternal(Double latitude, Double longitude, int page, int size) {
        GGetAllCafesAroundClientResponse response = rpcCoffeeHouseServiceClient.getAllCafesAroundClient(
                GGetAllCafesAroundClientRequest.newBuilder()
                        //.setAuthentication(internalAuthService.getGAuthentication())
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
                                //.setAuthentication(internalAuthService.getGAuthentication())
                                .setCafeUid(CommonConverter.convert(cafeUid))
                                .build())
                        .getCafe());
    }

    @Logged
    @Override
    public UUID addCafe(AddCoffeeHouseRequest request) {
        return CommonConverter.convert(
                rpcCoffeeHouseServiceClient.addCafe(
                        CoffeeHouseInfoConverter.convert(request))
                        .getCafeUid());
    }

    @Logged
    @Override
    public void updateCafe(UpdateCoffeeHouseRequest request) {
        rpcCoffeeHouseServiceClient.updateCafe(
                CoffeeHouseInfoConverter.convert(request));
    }

    @Logged
    @Override
    public void removeCafe(UUID cafeUid) {
        rpcCoffeeHouseServiceClient.removeCafe(
                GRemoveCafeRequest.newBuilder()
                        .setCafeUid(CommonConverter.convert(cafeUid))
                        .build());
    }
}
