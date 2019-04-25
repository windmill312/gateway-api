package com.github.windmill312.gateway.security;

import com.github.windmill312.gateway.exception.GatewayApiException;
import com.github.windmill312.gateway.exception.model.Service;
import com.github.windmill312.gateway.security.model.AuthenticationToken;
import com.github.windmill312.gateway.security.model.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityContextUtil {

    public static AuthenticationToken getAuthentication() {
        return (AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

    public static Principal getPrincipal() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(auth -> (Principal) auth.getPrincipal())
                .orElseThrow(() -> new GatewayApiException(
                        Service.GATEWAY,
                        HttpStatus.UNAUTHORIZED,
                        "No user logged in"));
    }
}
