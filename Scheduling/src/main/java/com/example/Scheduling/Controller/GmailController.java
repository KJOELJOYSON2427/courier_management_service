package com.example.Scheduling.Controller;

import com.example.Scheduling.accessToken.GmailAccessToken;
import com.example.Scheduling.dto.DeliveredEmailRequest;
import com.example.Scheduling.service.GmailService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

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
        return gmailAccessService.getAccessToken(auth);
    }

    // Hit this URL to send the email
    @PostMapping("/send-delivered")
    public ResponseEntity<?> sendDeliveredEmail(
            Authentication authentication,
            @RequestBody DeliveredEmailRequest request
    ) {
        try {
            OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
            System.out.println("MCame in" + auth);

            gmailService.sendDeliveredEmail(
                    auth,
                    request.getToEmail(),
                    request.getSenderName(),
                    request.getFrom(),
                    request.getTo(),
                    request.getRecipientName(),
                    request.getCost(),
                    request.getWeight(),
                    request.getNote()
            );
            return ResponseEntity.ok("Email sent successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Error");
        }
    }

    // Hit this URL to send the email
    @PostMapping("/send-welcome")
    public ResponseEntity<?> sendWelcomeEmail(
            Authentication authentication,
            @RequestBody WelcomeEmailRequest request
    ){
        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
        System.out.println("MCame in" + auth);
          gmailService.sendWelcomeEmail(request);
    }

}
