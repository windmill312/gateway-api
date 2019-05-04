package com.github.windmill312.gateway.service;

import com.github.windmill312.gateway.security.model.Authentication;
import com.github.windmill312.gateway.security.model.AuthenticationToken;
import com.github.windmill312.gateway.security.model.Principal;
import com.github.windmill312.gateway.web.to.in.LoginCustomerRequest;
import com.github.windmill312.gateway.web.to.in.UpdateTokenRequest;
import com.github.windmill312.gateway.web.to.out.CustomerInfo;
import com.github.windmill312.gateway.web.to.out.LoginInfo;

public interface AuthenticationService {

    LoginInfo login(LoginCustomerRequest request);

    Authentication getAuthentication(String token);

    void logout(AuthenticationToken authentication);

    CustomerInfo getCustomerInfo(Principal principal);

    LoginInfo refreshToken(UpdateTokenRequest request);
}
