package com.github.windmill312.gateway.grpc.client;

import com.github.windmill312.customer.grpc.model.v1.GAddCustomerRequest;
import com.github.windmill312.customer.grpc.model.v1.GAddCustomerResponse;
import com.github.windmill312.customer.grpc.model.v1.GGetAllCustomersRequest;
import com.github.windmill312.customer.grpc.model.v1.GGetAllCustomersResponse;
import com.github.windmill312.customer.grpc.model.v1.GGetCustomerRequest;
import com.github.windmill312.customer.grpc.model.v1.GGetCustomerResponse;
import com.github.windmill312.customer.grpc.service.v1.CustomerServiceV1Grpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GRpcCustomerServiceClient {

    private CustomerServiceV1Grpc.CustomerServiceV1BlockingStub customerServiceV1BlockingStub;

    @Value("#{new String('${gateway.grpc.client.OrderServiceV1Grpc.host}')}")
    private String host;
    @Value("#{new Integer('${gateway.grpc.client.OrderServiceV1Grpc.port}')}")
    private int port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        customerServiceV1BlockingStub = CustomerServiceV1Grpc.newBlockingStub(managedChannel);
    }

    public GGetAllCustomersResponse getAllCustomers(GGetAllCustomersRequest request) {
        return customerServiceV1BlockingStub.getAllCustomers(request);
    }

    public GGetCustomerResponse getCustomer(GGetCustomerRequest request) {
        return customerServiceV1BlockingStub.getCustomer(request);
    }

    public GAddCustomerResponse addCustomer(GAddCustomerRequest request) {
        return customerServiceV1BlockingStub.addCustomer(request);
    }


}
