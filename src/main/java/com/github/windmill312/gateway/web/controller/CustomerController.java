package com.github.windmill312.gateway.web.controller;

import com.github.windmill312.gateway.service.CustomerService;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddCustomerRequest;
import com.github.windmill312.gateway.web.to.out.AddCustomerInfo;
import com.github.windmill312.gateway.web.to.out.CustomerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<PagedResult<CustomerInfo>> getOrders(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        logger.debug("Getting all customers for page: {} and page size: {}", page, size);

        return ResponseEntity.ok(customerService.getAllCustomers(page, size));

    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<CustomerInfo> getCustomerInfo(@PathVariable UUID uuid) {
        logger.debug("Getting customer info by uid: {}", uuid);

        return ResponseEntity.ok(customerService.getCustomerByUid(uuid));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddCustomerInfo> addCustomer(@RequestBody @Valid AddCustomerRequest request) {
        logger.debug("Adding customer with name: {}", request.getName());

        return ResponseEntity.ok(new AddCustomerInfo(customerService.addCustomer(request)));
    }

}
