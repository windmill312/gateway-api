package com.github.windmill312.gateway.web.controller;

import com.github.windmill312.gateway.security.SecurityContextUtil;
import com.github.windmill312.gateway.security.model.AuthenticationToken;
import com.github.windmill312.gateway.service.AuthenticationService;
import com.github.windmill312.gateway.service.CustomerService;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddCustomerRequest;
import com.github.windmill312.gateway.web.to.in.LoginCustomerRequest;
import com.github.windmill312.gateway.web.to.in.UpdateTokenRequest;
import com.github.windmill312.gateway.web.to.out.AddCustomerInfo;
import com.github.windmill312.gateway.web.to.out.CustomerFullInfo;
import com.github.windmill312.gateway.web.to.out.IdentifierResponse;
import com.github.windmill312.gateway.web.to.out.LoginInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CustomersController {

    private static final Logger logger = LoggerFactory.getLogger(CustomersController.class);

    private final AuthenticationService authenticationService;
    private final CustomerService customerService;

    @Autowired
    public CustomersController(
            AuthenticationService authenticationService,
            CustomerService customerService) {
        this.authenticationService = authenticationService;
        this.customerService = customerService;
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginInfo> login(
            @RequestBody @Valid LoginCustomerRequest loginUserRequest) {

        logger.debug("Getting request to login by user: {}", loginUserRequest.getIdentifier());

        return ResponseEntity.ok(authenticationService.login(loginUserRequest));
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<LoginInfo> logout() {
        AuthenticationToken authentication = SecurityContextUtil.getAuthentication();

        logger.debug("Logout current customer");

        authenticationService.logout(authentication);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/identifier/{principalUid}")
    public ResponseEntity<IdentifierResponse> getIdentifier(@PathVariable UUID principalUid) {
        logger.debug("Getting identifier for customer");

        return ResponseEntity.ok(authenticationService.getIdentifier(principalUid));
    }

    @GetMapping(path = "/me")
    public ResponseEntity<CustomerFullInfo> me() {
        logger.debug("Getting info for current customer");

        return ResponseEntity.ok(authenticationService.getCustomerInfo(SecurityContextUtil.getPrincipal()));
    }

    @GetMapping
    public ResponseEntity<PagedResult<CustomerFullInfo>> getCustomers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        logger.debug("Getting all customers for page: {} and page size: {}", page, size);

        return ResponseEntity.ok(customerService.getAllCustomers(page, size));

    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<CustomerFullInfo> getCustomerInfo(@PathVariable UUID uuid) {
        logger.debug("Getting customer info by uid: {}", uuid);

        return ResponseEntity.ok(customerService.getCustomerByUid(uuid));
    }

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddCustomerInfo> addCustomer(@RequestBody @Valid AddCustomerRequest request) {
        logger.debug("Adding customer with name: {}", request.getName());

        return ResponseEntity.ok(new AddCustomerInfo(customerService.addCustomer(request)));
    }

    @PostMapping(path = "/refresh", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginInfo> refreshToken(@RequestBody @Valid UpdateTokenRequest request) {
        logger.debug("Updating customer token by customerUid:{}", request.getCustomerUid());

        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

    @DeleteMapping(path = "/{uuid}")
    public ResponseEntity<LoginInfo> deleteCustomer(@PathVariable UUID uuid) {
        logger.debug("Removing customer with uid:{}", uuid);

        customerService.removeCustomer(uuid);
        return ResponseEntity.noContent().build();
    }

    /*@PostMapping(path = "/admin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> setAdminGrants(@RequestBody @Valid CustomerUidRequest request) {

        logger.debug("Blocking customer with uid: {}", request.getCustomerUid());

        customerService.setAdminGrants(request.getCustomerUid());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/remove-admin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> removeAdminGrants(@RequestBody @Valid CustomerUidRequest request) {

        logger.debug("Unblocking customer with uid: {}", request.getCustomerUid());

        customerService.removeAdminGrants(request.getCustomerUid());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/block", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> blockCustomer(@RequestBody @Valid CustomerUidRequest request) {

        logger.debug("Blocking customer with uid: {}", request.getCustomerUid());

        customerService.blockCustomer(request.getCustomerUid());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/unblock", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> unblockCustomer(@RequestBody @Valid CustomerUidRequest request) {

        logger.debug("Unblocking customer with uid: {}", request.getCustomerUid());

        customerService.unblockCustomer(request.getCustomerUid());
        return ResponseEntity.noContent().build();
    }*/
}