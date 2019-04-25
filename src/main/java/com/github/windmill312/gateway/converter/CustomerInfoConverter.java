package com.github.windmill312.gateway.converter;

import com.github.windmill312.common.grpc.model.GPage;
import com.github.windmill312.customer.grpc.model.v1.GAddCustomerRequest;
import com.github.windmill312.customer.grpc.model.v1.GCustomerInfo;
import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddCustomerRequest;
import com.github.windmill312.gateway.web.to.out.CustomerFullInfo;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomerInfoConverter {

    public static PagedResult<CustomerFullInfo> convert(
            List<GCustomerInfo> CustomerInfoList, GPage page) {
        return new PagedResult<>(
                page.getTotalElements(),
                CustomerInfoList.stream()
                        .map(CustomerInfoConverter::convert)
                        .collect(Collectors.toList()));
    }

    public static CustomerFullInfo convert(GCustomerInfo customerInfo) {
        return new CustomerFullInfo(
                CommonConverter.convert(customerInfo.getExtId()),
                customerInfo.getName(),
                Instant.ofEpochMilli(customerInfo.getBirthDate()));
    }

    public static GAddCustomerRequest convert(AddCustomerRequest request, UUID customerExtId) {
        return GAddCustomerRequest.newBuilder()
                .setCustomer(GCustomerInfo.newBuilder()
                        .setName(request.getName())
                        .setBirthDate(request.getBirthDate().toEpochMilli())
                        .setExtId(CommonConverter.convert(customerExtId))
                        .build())
                .build();
    }

}
