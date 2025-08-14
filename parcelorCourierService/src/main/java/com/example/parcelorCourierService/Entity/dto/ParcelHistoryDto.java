package com.example.parcelorCourierService.Entity.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class ParcelHistoryDto {

    private String status;
    private String description;
    private LocalDateTime timestamp;
}
