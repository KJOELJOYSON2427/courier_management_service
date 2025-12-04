package com.example.parcelorCourierService.request;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateParcel {

    @Email(message = "Receiver email is invalid")
    private String receiverEmail;

    private String recieverName;
    private String recieverAddress;

    @Positive(message = "Cost must be greater than 0")
    private Double cost;

    @Positive(message = "Weight must be greater than 0")
    private Double weight;

    private String dimensions;

    private Long driverId;

    private String status; // optional update to status
}
