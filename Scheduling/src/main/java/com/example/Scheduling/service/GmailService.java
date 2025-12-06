package com.example.Scheduling.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.gmail.Gmail;
import org.springframework.stereotype.Service;

@Service
public class GmailService {
    public String sendMail(String accessToken,
                           String to,
                           String subject,
                           String body
    ) throws Exception{
        // 3. Build Gmail API client
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        HttpRequestInitializer requestInitializer =
                request -> request.getHeaders().setAuthorization("Bearer " + accessToken);
        Gmail gmail 
    }
}
