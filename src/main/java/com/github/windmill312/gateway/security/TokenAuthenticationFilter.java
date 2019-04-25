package com.github.windmill312.gateway.security;

import com.github.windmill312.gateway.security.model.Authentication;
import com.github.windmill312.gateway.security.model.AuthenticationToken;
import com.github.windmill312.gateway.service.AuthenticationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;
    private final TokenConfig tokenConfig;

    TokenAuthenticationFilter(
            AuthenticationService authenticationService,
            TokenConfig tokenConfig) {

        this.authenticationService = authenticationService;
        this.tokenConfig = tokenConfig;
    }

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        String header = request.getHeader(tokenConfig.getHeader());

        if (header == null || !header.startsWith(tokenConfig.getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(tokenConfig.getPrefix(), "");

        try {
            Authentication authentication = authenticationService.getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(
                    new AuthenticationToken(authentication.getToken(), authentication.getPrincipal(), null));

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}
