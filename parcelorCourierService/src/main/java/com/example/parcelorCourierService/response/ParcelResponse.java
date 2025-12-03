package com.example.parcelorCourierService.response;



import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ParcelResponse {

    private String trackingNumber;
    private String senderName;
    private String senderEmail;
    private String senderAddress;

    private String receiverEmail;
    private String recieverName;
    private String recieverAddress;

    private double cost;
    private double weight;
    private String dimensions;

    private Long driverId;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
