package com.github.windmill312.gateway.web.controller;

import com.github.windmill312.gateway.config.OAuthConfig;
import com.github.windmill312.gateway.security.SecurityContextUtil;
import com.github.windmill312.gateway.security.model.AuthenticationToken;
import com.github.windmill312.gateway.service.OAuthService;
import com.github.windmill312.gateway.web.to.in.OAuthRefreshTokenRequest;
import com.github.windmill312.gateway.web.to.in.OAuthTokenRequest;
import com.github.windmill312.gateway.web.to.out.OAuthCodeResponse;
import com.github.windmill312.gateway.web.to.out.OAuthTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/oauth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OAuthController {

    private static final Logger logger = LoggerFactory.getLogger(OAuthController.class);

    private final OAuthConfig oAuthConfig;
    private final OAuthService oAuthService;

    @Autowired
    public OAuthController(
            OAuthConfig oAuthConfig,
            OAuthService oAuthService) {
        this.oAuthConfig = oAuthConfig;
        this.oAuthService = oAuthService;
    }

    @GetMapping
    public RedirectView requestAuthorization(
            @RequestParam("clientId") String clientId,
            @RequestParam("redirectUri") String redirectUri) {

        logger.debug("Getting request for authorization from client {}", clientId);

        return new RedirectView(String.format(oAuthConfig.getRedirectOauthUriTemplate(), clientId, redirectUri));
    }

    @GetMapping("/authorize")
    public String authorize(
            @RequestParam("clientId") String clientId,
            @RequestParam("redirectUri") String redirectUri,
            HttpServletRequest httpServletRequest) {

        logger.debug("Authorizing client {}", clientId);

        AuthenticationToken authentication = SecurityContextUtil.getAuthentication();
        OAuthCodeResponse codeResponse = oAuthService.authorize(clientId, authentication);

        return codeResponse.getAuthorizationCode();
    }

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OAuthTokenResponse> getOAuthToken(@RequestBody @Valid OAuthTokenRequest request) {

        logger.debug("Getting OAuth token for client {}", request.getClientId());

        return ResponseEntity.ok(oAuthService.getToken(request));
    }

    @PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OAuthTokenResponse> refreshOAuthToken(@RequestBody @Valid OAuthRefreshTokenRequest request) {

        logger.debug("Refreshing OAuth token for client {}", request.getClientId());

        return ResponseEntity.ok(oAuthService.refreshToken(request));
    }
}
