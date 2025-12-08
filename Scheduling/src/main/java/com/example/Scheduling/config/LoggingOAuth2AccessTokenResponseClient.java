package com.example.Scheduling.config;

import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingOAuth2AccessTokenResponseClient
        implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingOAuth2AccessTokenResponseClient.class);
    private final DefaultAuthorizationCodeTokenResponseClient delegate = new DefaultAuthorizationCodeTokenResponseClient();

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        OAuth2AccessTokenResponse tokenResponse = delegate.getTokenResponse(authorizationGrantRequest);

        logger.info("=== GOOGLE RAW TOKEN RESPONSE ===");
        logger.info("Access Token: {}", tokenResponse.getAccessToken().getTokenValue());
        logger.info("Refresh Token: {}", tokenResponse.getRefreshToken() != null ? tokenResponse.getRefreshToken().getTokenValue() : "NULL");
        logger.info("Scopes: {}", tokenResponse.getAccessToken().getScopes());
        logger.info("Expires At: {}", tokenResponse.getAccessToken().getExpiresAt());
        logger.info("==============================");

        return tokenResponse;
    }
}
