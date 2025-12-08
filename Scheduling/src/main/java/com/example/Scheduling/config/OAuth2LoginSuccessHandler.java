package com.example.Scheduling.config;

import com.example.Scheduling.refresh_token.GmailRefreshTokenForSheduler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final GmailRefreshTokenForSheduler gmailTokenService;
    private  final OAuth2AuthorizedClientService clientService;
    public OAuth2LoginSuccessHandler(GmailRefreshTokenForSheduler gmailTokenService, OAuth2AuthorizedClientService clientService) {
        this.gmailTokenService = gmailTokenService;
        this.clientService = clientService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {



        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient client =
                clientService.loadAuthorizedClient(
                        authToken.getAuthorizedClientRegistrationId(),
                        authToken.getName()
                );
        System.out.println("=== GOOGLE TOKEN DETAILS ===");

        System.out.println("Client Registration ID: " + client.getClientRegistration().getRegistrationId());

        System.out.println("Access Token Value: " + client.getAccessToken().getTokenValue());
        System.out.println("Access Token Scopes: " + client.getAccessToken().getScopes());
        System.out.println("Access Token Issued At: " + client.getAccessToken().getIssuedAt());
        System.out.println("Access Token Expires At: " + client.getAccessToken().getExpiresAt());

        if (client.getRefreshToken() != null) {
            System.out.println("Refresh Token Value: " + client.getRefreshToken().getTokenValue());
            System.out.println("Refresh Token Issued At: " + client.getRefreshToken().getIssuedAt());
        } else {
            System.out.println("Refresh Token: NULL");
        }

        System.out.println("=== GOOGLE OAUTH DETAILS END ===");

        String accessToken = client.getAccessToken().getTokenValue();

        String refreshToken = client.getRefreshToken() != null
                ? client.getRefreshToken().getTokenValue()
                :null;
        Instant expiryTime = client.getAccessToken().getExpiresAt();
        gmailTokenService.saveTokens(accessToken, refreshToken, expiryTime);

        response.sendRedirect("/home");
    }

}
