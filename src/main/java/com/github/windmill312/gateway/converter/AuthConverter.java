package com.github.windmill312.gateway.converter;

import com.github.windmill312.auth.grpc.model.v1.GAuthentication;
import com.github.windmill312.auth.grpc.model.v1.GCredentials;
import com.github.windmill312.auth.grpc.model.v1.GFingerPrint;
import com.github.windmill312.auth.grpc.model.v1.GFullAuthentication;
import com.github.windmill312.auth.grpc.model.v1.GLogin;
import com.github.windmill312.auth.grpc.model.v1.GOAuthRefreshTokenResponse;
import com.github.windmill312.auth.grpc.model.v1.GOAuthTokenResponse;
import com.github.windmill312.auth.grpc.model.v1.GPrincipalOuterKey;
import com.github.windmill312.auth.grpc.model.v1.GSecret;
import com.github.windmill312.auth.grpc.model.v1.GToken;
import com.github.windmill312.gateway.security.model.Authentication;
import com.github.windmill312.gateway.security.model.FullAuthentication;
import com.github.windmill312.gateway.security.model.Principal;
import com.github.windmill312.gateway.web.to.in.LoginCustomerRequest;
import com.github.windmill312.gateway.web.to.out.OAuthTokenResponse;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.UUID;

public class AuthConverter {
    public static Authentication toAuthentication(GAuthentication authentication) {
        return new Authentication(
                authentication.getToken().getToken(),
                toPrincipal(authentication.getPrincipal()));
    }

    public static FullAuthentication toFullAuthentication(GFullAuthentication authentication) {
        return new FullAuthentication(
                authentication.getAccessToken().getToken(),
                authentication.getRefreshToken(),
                toPrincipal(authentication.getPrincipal()));
    }

    public static GAuthentication toGAuthentication(Authentication authentication) {
        return GAuthentication.newBuilder()
                .setToken(GToken.newBuilder().setToken(authentication.getToken()).build())
                .setPrincipal(GPrincipalOuterKey.newBuilder()
                        .setExtId(authentication.getPrincipal().getExtId().toString())
                        .setSubsystemCode(authentication.getPrincipal().getSubsystem())
                        .build())
                .build();
    }

    public static GAuthentication toGAuthentication(FullAuthentication authentication) {
        return GAuthentication.newBuilder()
                .setToken(GToken.newBuilder().setToken(authentication.getAccessToken()).build())
                .setPrincipal(GPrincipalOuterKey.newBuilder()
                        .setExtId(authentication.getPrincipal().getExtId().toString())
                        .setSubsystemCode(authentication.getPrincipal().getSubsystem())
                        .build())
                .build();
    }

    public static GFullAuthentication toGFullAuthentication(FullAuthentication authentication) {
        return GFullAuthentication.newBuilder()
                .setAccessToken(GToken.newBuilder().setToken(authentication.getAccessToken()).build())
                .setRefreshToken(authentication.getRefreshToken())
                .setPrincipal(GPrincipalOuterKey.newBuilder()
                        .setExtId(authentication.getPrincipal().getExtId().toString())
                        .setSubsystemCode(authentication.getPrincipal().getSubsystem())
                        .build())
                .build();
    }

    public static Principal toPrincipal(GPrincipalOuterKey principal) {
        return new Principal(
                principal.getSubsystemCode(),
                UUID.fromString(principal.getExtId()));
    }

    public static GFingerPrint toGFingerPrint(HttpServletRequest request) {
        return GFingerPrint.newBuilder()
                .build();
    }

    public static GCredentials toGCredentials(LoginCustomerRequest request) {
        return GCredentials.newBuilder()
                .setLogin(toGLogin(request.getIdentifier()))
                .setSecret(toGSecret(request.getPassword()))
                .build();
    }

    public static GCredentials toGCredentials(String login, String secret) {
        return GCredentials.newBuilder()
                .setLogin(toGLogin(login))
                .setSecret(toGSecret(secret))
                .build();
    }

    public static OAuthTokenResponse convert(GOAuthTokenResponse response) {
        return new OAuthTokenResponse(
                response.getAccessToken(),
                response.getTokenType(),
                Instant.ofEpochMilli(response.getExpiresIn()),
                response.getRefreshToken());
    }

    public static OAuthTokenResponse convert(GOAuthRefreshTokenResponse response) {
        return new OAuthTokenResponse(
                response.getAccessToken(),
                response.getTokenType(),
                Instant.ofEpochMilli(response.getExpiresIn()),
                response.getRefreshToken());
    }

    private static GLogin toGLogin(String login) {
        return GLogin.newBuilder()
                .setType(GLogin.GLoginType.EMAIL)
                .setValue(login)
                .build();
    }

    private static GSecret toGSecret(String secret) {
        return GSecret.newBuilder()
                .setType(GSecret.GSecretType.PASSWORD)
                .setValue(secret)
                .build();
    }
}
