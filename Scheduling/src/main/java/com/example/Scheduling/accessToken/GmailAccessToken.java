package com.example.Scheduling.accessToken;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class GmailAccessToken {

    private final OAuth2AuthorizedClientService clientService;


    public GmailAccessToken(OAuth2AuthorizedClientService clientService) {
        this.clientService = clientService;
    }


    public String getAccessToken(OAuth2AuthenticationToken auth){
      String clientName = auth.getAuthorizedClientRegistrationId();
      System.out.println("clientName --->" + clientName);
        System.out.println("auth --->" + auth);

        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(clientName, auth.getName());
        System.out.println("client --->" + client);

        String accessToken = client.getAccessToken().getTokenValue();
        return "Your Gmail Access Token: " + accessToken;

    }
}
