package com.github.windmill312.gateway.service;

import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddCoffeeHouseRequest;
import com.github.windmill312.gateway.web.to.in.UpdateCoffeeHouseRequest;
import com.github.windmill312.gateway.web.to.out.CoffeeHouseInfo;

import java.util.UUID;

public interface CoffeeHouseService {

    PagedResult<CoffeeHouseInfo> getCafes(Double latitude, Double longitude, int page, int size);

    CoffeeHouseInfo getCafeByUid(UUID cafeUid);

    UUID addCafe(AddCoffeeHouseRequest request);

    void updateCafe(UpdateCoffeeHouseRequest request);

    void removeCafe(UUID cafeUid);
}
