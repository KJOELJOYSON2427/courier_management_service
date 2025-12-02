package com.microservice.driver_service.openFeign;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Data
public class ParcelHistoryDto {
    private Long parcelId;
    private String status;
    private String description;
    private LocalDateTime timestamp;
}
