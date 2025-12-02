package com.example.parcelorCourierService.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ParcelRequest {

    // Getters & Setters
    private Long parcelId;        // Internal use (optional)
    private Long senderId;        // From User DB

    // SENDER DETAILS (REQUIRED)
    private String senderName;
    private String senderEmail;
    private String senderAddress;

    // RECEIVER DETAILS (REQUIRED)
    private String receiverName;
    private String receiverAddress;
    private String receiverEmail;

    // PARCEL DETAILS
    private double weight;
    private String dimensions;
    private double cost;

    private Long parcelGetId;

    private String trackingNumber; // Generated or user-provided

    // Optional: status (usually NOT sent from client)
    private String status; // Optional — use ParcelStatus enum in service layer

}
