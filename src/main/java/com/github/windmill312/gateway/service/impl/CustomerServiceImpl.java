package com.github.windmill312.gateway.service.impl;

import com.github.windmill312.customer.grpc.model.v1.GGetAllCustomersRequest;
import com.github.windmill312.customer.grpc.model.v1.GGetAllCustomersResponse;
import com.github.windmill312.customer.grpc.model.v1.GGetCustomerRequest;
import com.github.windmill312.gateway.annotation.GatewayService;
import com.github.windmill312.gateway.annotation.Logged;
import com.github.windmill312.gateway.converter.CommonConverter;
import com.github.windmill312.gateway.converter.CustomerInfoConverter;
import com.github.windmill312.gateway.grpc.client.GRpcCustomerServiceClient;
import com.github.windmill312.gateway.service.CustomerService;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddCustomerRequest;
import com.github.windmill312.gateway.web.to.out.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.windmill312.gateway.exception.model.Service.ORDER;

@GatewayService(serviceName = ORDER)
public class CustomerServiceImpl implements CustomerService {

    private final GRpcCustomerServiceClient rpcCustomerServiceClient;

    @Autowired
    public CustomerServiceImpl(GRpcCustomerServiceClient rpcCustomerServiceClient) {
        this.rpcCustomerServiceClient = rpcCustomerServiceClient;
    }

    @Logged
    @Override
    public PagedResult<CustomerInfo> getAllCustomers(int page, int size) {
        GGetAllCustomersResponse response = rpcCustomerServiceClient.getAllCustomers(
                GGetAllCustomersRequest.newBuilder()
                        //.setAuthentication(internalAuthService.getGAuthentication())
                        .setPageable(CommonConverter.convert(page, size))
                        .build());

        return CustomerInfoConverter.convert(response.getCustomersList(), response.getPage());
    }

    @Logged
    @Override
    public CustomerInfo getCustomerByUid(UUID customerUid) {
        return CustomerInfoConverter.convert(
                rpcCustomerServiceClient.getCustomer(
                        GGetCustomerRequest.newBuilder()
                                .setCustomerUid(
                                        CommonConverter.convert(customerUid)).build()
                ).getCustomer()
        );
    }

    @Logged
    @Override
    public UUID addCustomer(AddCustomerRequest request) {
        return CommonConverter.convert(
                rpcCustomerServiceClient.addCustomer(
                        CustomerInfoConverter.convert(request))
                .getCustomerUid());
    }
}
