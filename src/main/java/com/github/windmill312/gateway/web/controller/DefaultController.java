package com.github.windmill312.gateway.web.controller;

import com.github.windmill312.gateway.service.AuthenticationService;
import com.github.windmill312.gateway.service.CoffeeHouseService;
import com.github.windmill312.gateway.service.OrderService;
import com.github.windmill312.gateway.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class DefaultController {

    /*private final OrderService orderService;
    private final AuthenticationService authService;
    private final CoffeeHouseService coffeeHouseService;
    private final ProductService productService;

    public DefaultController(
            OrderService orderService,
            AuthenticationService authService,
            CoffeeHouseService coffeeHouseService,
            ProductService productService) {
        this.orderService = orderService;
        this.authService = authService;
        this.coffeeHouseService = coffeeHouseService;
        this.productService = productService;
    }*/

    @RequestMapping({"/", "/api"})
    public String home() {
        return "redirect:swagger-ui.html";
    }

    /*@RequestMapping(path = "/api/v1/availability")
    public ResponseEntity<Void> isAvailableService(@RequestParam String service) {
        switch (service) {
            case "order":
                return orderService.isAvailable() ? ResponseEntity.ok().build() : ResponseEntity.status(503).build();
                break;
            case "product":
                return productService.isAvailable() ? ResponseEntity.ok().build() : ResponseEntity.status(503).build();
                break;
            case "coffeehouse":
                return coffeeHouseService.isAvailable() ? ResponseEntity.ok().build() : ResponseEntity.status(503).build();
                break;
            case "auth":
                return authService.isAvailable() ? ResponseEntity.ok().build() : ResponseEntity.status(503).build();
                break;
        }
        return ResponseEntity.badRequest().build();
    }*/
}