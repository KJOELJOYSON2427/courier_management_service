package com.example.parcelorCourierService.request;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateParcel {

    // ===== Receiver Details =====
    @Email(message = "Receiver email is invalid")
    private String receiverEmail;

    private String recieverName;        // keep as-is
    private String recieverAddress;     // keep as-is

    // ===== Cost & Weight =====
    @Positive(message = "Cost must be greater than 0")
    private Double cost;

    @Positive(message = "Weight must be greater than 0")
    private Double weight;

    // ===== Extra Parcel Info =====
    private String dimensions;
    private String status;

    // ===== Newly Added (from Angular form) =====
    private String from;               // sender address
    private String to;                 // receiver address (UI field)
    private String senderName;
    private String senderEmail;

    @Email(message = "Recipient email is invalid")
    private String recipientEmail;
private Long SenderId;
    private String date;
    private String note;
}
