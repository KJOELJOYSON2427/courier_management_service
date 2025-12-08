package com.example.Scheduling.dto;

import lombok.Data;

@Data
public class DeliveredEmailRequest {
    private String toEmail;
    private String senderName;
    private String from;
    private String to;
    private String recipientName;
    private double cost;
    private String weight;
    private String note;
}
