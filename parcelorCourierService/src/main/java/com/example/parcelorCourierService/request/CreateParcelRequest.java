package com.example.parcelorCourierService.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Data
public class CreateParcelRequest {




    // Sender details
    @NotBlank(message = "Sender name is required")
    private String senderName;

    @Email(message = "Sender email is not valid")
    @NotBlank(message = "Sender email is required")
    private String senderEmail;

    @NotBlank(message = "Sender address is required")
    private String from;

    // Receiver details
    @Email(message = "Receiver email is not valid")
    @NotBlank(message = "Receiver email is required")
    private String recipientEmail;

    @NotBlank(message = "Receiver name is required")
    private String recipientName;

    @NotBlank(message = "Receiver address is required")
    private String to;

    // Parcel details
    @Positive(message = "Cost must be greater than 0")
    private double cost;

    private Long senderId;

    @Positive(message = "Weight must be greater than 0")
    private double weight;

    @NotBlank(message = "Dimensions field cannot be empty")
    private String dimensions;

    @NotBlank(message = "Note field cannot be empty")
    private String note;




}
