package com.example.Scheduling.refresh_token;

import com.example.Scheduling.CronJob.GmailProperties;
import com.example.Scheduling.gmailRepo.GmailTokenRepository;
import com.example.Scheduling.gmailToken.GmailToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class GmailRefreshTokenForSheduler {

    private final GmailTokenRepository repo;
    private final GmailProperties gmailProperties;


    public GmailRefreshTokenForSheduler(GmailTokenRepository repo, GmailProperties gmailProperties) {
        this.repo = repo;
        this.gmailProperties = gmailProperties;
    }

    public String getValidAccessToken() throws Exception {

        GmailToken token = repo.findById(1L).orElse(null);

        if (token == null || token.getRefreshToken() == null) {
            throw new RuntimeException("❌ No Gmail refresh token saved!");
        }

        // If access token is still valid → return it
        if (token.getExpiryTimeMillis() != null &&
                token.getExpiryTimeMillis() > System.currentTimeMillis()) {
            return token.getAccessToken();
        }

        // Otherwise refresh it
        return refreshAccessToken(token);
    }


    private String refreshAccessToken(GmailToken tokenObj) throws Exception {

        GoogleTokenResponse response =
                new GoogleRefreshTokenRequest(
                        new NetHttpTransport(),
                        new GsonFactory(),
                        tokenObj.getRefreshToken(),
                        gmailProperties.getClientId(),
                        gmailProperties.getClientSecret()
                ).execute();

        String newAccessToken = response.getAccessToken();

        // Save new token + expiry

        tokenObj.setAccessToken(newAccessToken);

        tokenObj.setExpiryTimeMillis(
                System.currentTimeMillis() + (response.getExpiresInSeconds() * 1000)
        );

        repo.save(tokenObj);

        return newAccessToken;
    }


    public void saveTokens(String access, String refresh, Instant expirty) {
        GmailToken token = repo.findById(1L).orElse(new GmailToken());
        token.setAccessToken(access);

        if (refresh != null) token.setRefreshToken(refresh);
        token.setExpiryTimeMillis(expirty.toEpochMilli());
        repo.save(token);

        System.out.println("🔥 Gmail OAuth tokens saved!");
    }

}
