package com.example.parcelorCourierService.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CreateParcelRequest {



    @NotBlank(message = "Tracking number is required")
    private String trackingNumber;

    // Sender details
    @NotBlank(message = "Sender name is required")
    private String senderName;

    @Email(message = "Sender email is not valid")
    @NotBlank(message = "Sender email is required")
    private String senderEmail;

    @NotBlank(message = "Sender address is required")
    private String senderAddress;

    // Receiver details
    @Email(message = "Receiver email is not valid")
    @NotBlank(message = "Receiver email is required")
    private String receiverEmail;

    @NotBlank(message = "Receiver name is required")
    private String recieverName;

    @NotBlank(message = "Receiver address is required")
    private String recieverAddress;

    // Parcel details
    @Positive(message = "Cost must be greater than 0")
    private double cost;

    private Long senderId;

    @Positive(message = "Weight must be greater than 0")
    private double weight;

    @NotBlank(message = "Dimensions field cannot be empty")
    private String dimensions;

    private Long driverId; // optional
}
