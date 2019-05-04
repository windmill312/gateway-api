package com.github.windmill312.gateway.service;

import com.github.windmill312.gateway.web.to.common.PagedResult;
import com.github.windmill312.gateway.web.to.in.AddCustomerRequest;
import com.github.windmill312.gateway.web.to.in.UpdateTokenRequest;
import com.github.windmill312.gateway.web.to.out.CustomerFullInfo;
import com.github.windmill312.gateway.web.to.out.LoginInfo;

import java.util.UUID;

public interface CustomerService {

    PagedResult<CustomerFullInfo> getAllCustomers(int page, int size);

    CustomerFullInfo getCustomerByUid(UUID customerUid);

    UUID addCustomer(AddCustomerRequest request);
}
