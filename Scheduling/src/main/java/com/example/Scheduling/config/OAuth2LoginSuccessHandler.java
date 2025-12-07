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

        String accessToken = client.getAccessToken().getTokenValue();

        String refreshToken = client.getRefreshToken() != null
                ? client.getRefreshToken().getTokenValue()
                :null;

        gmailTokenService.saveTokens(accessToken, refreshToken);

        response.sendRedirect("/home");
    }

}
