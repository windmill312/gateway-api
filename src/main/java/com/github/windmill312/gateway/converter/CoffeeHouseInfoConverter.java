package com.github.windmill312.gateway.converter;

import com.github.windmill312.coffeehouse.grpc.model.v1.GAddCafeRequest;
import com.github.windmill312.coffeehouse.grpc.model.v1.GCafeInfo;
import com.github.windmill312.coffeehouse.grpc.model.v1.GLocation;
import com.github.windmill312.coffeehouse.grpc.model.v1.GUpdateCafeRequest;
import com.github.windmill312.common.grpc.model.GPage;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddCoffeeHouseRequest;
import com.github.windmill312.gateway.web.to.in.UpdateCoffeeHouseRequest;
import com.github.windmill312.gateway.web.to.out.CoffeeHouseInfo;

import java.util.List;
import java.util.stream.Collectors;

public class CoffeeHouseInfoConverter {

    public static PagedResult<CoffeeHouseInfo> convert(
            List<GCafeInfo> cafeInfoList, GPage page) {
        return new PagedResult<>(
                page.getTotalElements(),
                cafeInfoList.stream()
                        .map(CoffeeHouseInfoConverter::convert)
                        .collect(Collectors.toList()));
    }

    public static CoffeeHouseInfo convert(GCafeInfo cafeInfo) {
        return new CoffeeHouseInfo(
                CommonConverter.convert(cafeInfo.getCafeUid()),
                cafeInfo.getLocation().getLatitude(),
                cafeInfo.getLocation().getLongitude(),
                cafeInfo.getCafeName(),
                cafeInfo.getDescription(),
                CommonConverter.convert(cafeInfo.getOwnerUid()));
    }

    public static GAddCafeRequest convert(AddCoffeeHouseRequest request) {
        return GAddCafeRequest.newBuilder()
                .setCafe(GCafeInfo.newBuilder()
                        .setLocation(
                                GLocation.newBuilder()
                                        .setLongitude(request.getLongitude())
                                        .setLatitude(request.getLatitude())
                                        .build())
                        .setOwnerUid(CommonConverter.convert(request.getOwnerUid()))
                        .setDescription(request.getDescription())
                        .setCafeName(request.getName()))
                .build();
    }

    public static GUpdateCafeRequest convert(UpdateCoffeeHouseRequest request) {
        return GUpdateCafeRequest.newBuilder()
                .setCafe(GCafeInfo.newBuilder()
                        .setCafeUid(CommonConverter.convert(request.getCafeUid()))
                        .setLocation(
                                GLocation.newBuilder()
                                        .setLongitude(request.getLongitude())
                                        .setLatitude(request.getLatitude())
                                        .build())
                        .setOwnerUid(CommonConverter.convert(request.getOwnerUid()))
                        .setDescription(request.getDescription())
                        .setCafeName(request.getName()))
                .build();
    }

}
