package com.example.Scheduling.Controller;

import com.example.Scheduling.accessToken.GmailAccessToken;
import com.example.Scheduling.service.GmailService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GmailController {

    private final GmailAccessToken gmailAccessService;
    private final GmailService gmailService;

    public GmailController(GmailAccessToken gmailAccessService, GmailService gmailSendService) {
        this.gmailAccessService = gmailAccessService;
        this.gmailService = gmailSendService;
    }

    @GetMapping("/gmail")
    public String readAccessToken(Authentication authentication) {
        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
        System.out.println("The read Mail " + gmailAccessService.getAccessToken(auth));
        return gmailAccessService.getAccessToken(auth)
    }

    @GetMapping("/send-email")
    public String sendEmail(
            Authentication authentication,
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body
    )throws Exception {
        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
        String accessToken = gmailAccessService.getAccessToken(auth);
        return gmailService.sendMail(auth, to, subject, body);
    }
}
