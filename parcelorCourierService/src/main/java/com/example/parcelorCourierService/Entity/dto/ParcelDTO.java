package com.example.parcelorCourierService.Entity.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Setter
@Getter
public class ParcelDTO {

    private Long id;

    private  Long senderId;
    private Long parcelGetId;
    private String receiverName;
    private String trackingNumber; // NEW FIELD
    private String receiverAddress;
    
    private double weight;
    
    private String dimensions;
    private String status;
    private Long driverId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ParcelHistoryDto> history;


}
