package com.sychev.gateway.service;

import com.sychev.gateway.web.to.common.PagedResult;
import com.sychev.gateway.web.to.in.AddCoffeeHouseRequest;
import com.sychev.gateway.web.to.in.UpdateCoffeeHouseRequest;
import com.sychev.gateway.web.to.out.CoffeeHouseInfo;

import java.util.UUID;

public interface CoffeeHouseService {

    PagedResult<CoffeeHouseInfo> getCafes(Double latitude, Double longitude, int page, int size);

    CoffeeHouseInfo getCafeByUid(UUID cafeUid);

    UUID addCafe(AddCoffeeHouseRequest request);

    void updateCafe(UpdateCoffeeHouseRequest request);

    void removeCafe(UUID cafeUid);
}
