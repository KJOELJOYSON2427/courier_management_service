package com.example.Scheduling.CronJob;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "gmail")
public class GmailProperties {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String from;
}