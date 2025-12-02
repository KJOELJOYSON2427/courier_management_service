package com.microservice.driver_service.openFeign;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
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
