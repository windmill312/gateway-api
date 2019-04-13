package com.github.windmill312.gateway.web.controller;

import com.github.windmill312.gateway.service.CoffeeHouseService;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddCoffeeHouseRequest;
import com.github.windmill312.gateway.web.to.in.UpdateCoffeeHouseRequest;
import com.github.windmill312.gateway.web.to.out.AddCoffeeHouseInfo;
import com.github.windmill312.gateway.web.to.out.CoffeeHouseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/coffeehouses", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CoffeeHouseController {

    private static final Logger logger = LoggerFactory.getLogger(CoffeeHouseController.class);

    private final CoffeeHouseService coffeeHouseService;

    @Autowired
    public CoffeeHouseController(CoffeeHouseService coffeeHouseService) {
        this.coffeeHouseService = coffeeHouseService;
    }

    @GetMapping
    public ResponseEntity<PagedResult<CoffeeHouseInfo>> getCafes(
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "longitude", required = false) Double longitude,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        logger.debug("Getting all cafes for page: {} and page size: {} by latitude: {} and longitude: {}", page, size, latitude, longitude);

        return ResponseEntity.ok(coffeeHouseService.getCafes(latitude, longitude, page, size));
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<CoffeeHouseInfo> getCafeInfo(@PathVariable UUID uuid) {
        logger.debug("Getting cafe info by uid: {}", uuid);

        return ResponseEntity.ok(coffeeHouseService.getCafeByUid(uuid));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddCoffeeHouseInfo> addCafe(@RequestBody @Valid AddCoffeeHouseRequest request) {
        logger.debug("Adding cafe with name: {}", request.getName());

        return ResponseEntity.ok(new AddCoffeeHouseInfo(coffeeHouseService.addCafe(request)));
    }


    @PatchMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> updateCafe(@RequestBody @Valid UpdateCoffeeHouseRequest request) {
        logger.debug("Updating cafe with name: {}", request.getName());

        coffeeHouseService.updateCafe(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{uuid}")
    public ResponseEntity<Void> deleteCafe(@PathVariable UUID uuid) {
        logger.debug("Deleting cafe with uid: {}", uuid);

        coffeeHouseService.removeCafe(uuid);
        return ResponseEntity.noContent().build();
    }

}
