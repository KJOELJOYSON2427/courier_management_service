package com.example.parcelorCourierService.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ParcelRequest {
    private Long senderId; // From user DB
    private String receiverName;
    private String receiverAddress;
    private double weight;
    private String dimensions;
    private String trackingNumber; // NEW FIELD
    // Getters and Setters
}
