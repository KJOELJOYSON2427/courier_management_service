package com.example.Scheduling.gmailToken;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gmail_token")
public class GmailToken {

    @Id
    private Long id = 1L; // always ONE row

    private String refreshToken;

    private String accessToken;

    private Long expiryTimeMillis;

    // getters + setters
}
