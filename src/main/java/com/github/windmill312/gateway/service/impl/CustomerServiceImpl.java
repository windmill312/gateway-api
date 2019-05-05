package com.github.windmill312.gateway.service.impl;

import com.github.windmill312.auth.grpc.model.v1.GAddCredentialsRequest;
import com.github.windmill312.auth.grpc.model.v1.GAddPrincipalRequest;
import com.github.windmill312.auth.grpc.model.v1.GDeletePrincipalRequest;
import com.github.windmill312.auth.grpc.model.v1.GPrincipalOuterKey;
import com.github.windmill312.customer.grpc.model.v1.GGetAllCustomersRequest;
import com.github.windmill312.customer.grpc.model.v1.GGetAllCustomersResponse;
import com.github.windmill312.customer.grpc.model.v1.GGetCustomerRequest;
import com.github.windmill312.gateway.annotation.GatewayService;
import com.github.windmill312.gateway.annotation.Logged;
import com.github.windmill312.gateway.converter.AuthConverter;
import com.github.windmill312.gateway.converter.CommonConverter;
import com.github.windmill312.gateway.converter.CustomerInfoConverter;
import com.github.windmill312.gateway.grpc.client.GRpcCredentialsServiceClient;
import com.github.windmill312.gateway.grpc.client.GRpcCustomerServiceClient;
import com.github.windmill312.gateway.grpc.client.GRpcPrincipalServiceClient;
import com.github.windmill312.gateway.security.InternalAuthService;
import com.github.windmill312.gateway.service.CustomerService;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddCustomerRequest;
import com.github.windmill312.gateway.web.to.out.CustomerFullInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.github.windmill312.gateway.exception.model.Service.ORDER;

@GatewayService(serviceName = ORDER)
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final InternalAuthService internalAuthService;
    private final GRpcCustomerServiceClient rpcCustomerServiceClient;
    private final GRpcPrincipalServiceClient rpcPrincipalServiceClient;
    private final GRpcCredentialsServiceClient rpcCredentialsServiceClient;

    @Autowired
    public CustomerServiceImpl(
            InternalAuthService internalAuthService,
            GRpcCustomerServiceClient rpcCustomerServiceClient,
            GRpcPrincipalServiceClient rpcPrincipalServiceClient,
            GRpcCredentialsServiceClient rpcCredentialsServiceClient) {
        this.internalAuthService = internalAuthService;
        this.rpcCustomerServiceClient = rpcCustomerServiceClient;
        this.rpcPrincipalServiceClient = rpcPrincipalServiceClient;
        this.rpcCredentialsServiceClient = rpcCredentialsServiceClient;
    }

    @Logged
    @Override
    public PagedResult<CustomerFullInfo> getAllCustomers(int page, int size) {
        GGetAllCustomersResponse response = rpcCustomerServiceClient.getAllCustomers(
                GGetAllCustomersRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return CustomerInfoConverter.convert(response.getCustomersList(), response.getPage());
    }

    @Logged
    @Override
    public CustomerFullInfo getCustomerByUid(UUID customerUid) {
        return CustomerInfoConverter.convert(
                rpcCustomerServiceClient.getCustomer(
                        GGetCustomerRequest.newBuilder()
                                .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                                .setCustomerUid(
                                        CommonConverter.convert(customerUid)).build()
                ).getCustomer()
        );
    }

    @Logged
    @Override
    public UUID addCustomer(AddCustomerRequest request) {
        GPrincipalOuterKey principal = rpcPrincipalServiceClient.addPrincipal(
                GAddPrincipalRequest.newBuilder()
                        .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                        .setPrincipalExtId(UUID.randomUUID().toString())
                        .setSubsystemCode(request.getSubsystemCode())
                        .build())
                .getPrincipalKey();

        try {
            rpcCredentialsServiceClient.addCredentials(
                    GAddCredentialsRequest.newBuilder()
                            .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                            .setPrincipalKey(principal)
                            .setCredentials(AuthConverter.toGCredentials(request.getIdentifier(), request.getPassword()))
                            .build());

            return CommonConverter.convert(
                    rpcCustomerServiceClient.addCustomer(CustomerInfoConverter.convert(
                            request,
                            UUID.fromString(principal.getExtId())))
                            .getCustomerUid());

        } catch (Exception ex) {
            logger.warn("Failed to add customer: " + principal.getExtId());

            rpcPrincipalServiceClient.deletePrincipal(
                    GDeletePrincipalRequest.newBuilder()
                            .setAuthentication(AuthConverter.toGAuthentication(internalAuthService.getInternalAuthentication()))
                            .setPrincipalExtId(principal.getExtId())
                            .setSubsystemCode(request.getSubsystemCode())
                            .build()
            );

            throw ex;
        }
    }
}
