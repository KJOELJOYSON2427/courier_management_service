package com.microservice.driver_service.RequestDTO;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParcelAssignRequest {
    private List<Long> parcelId;
}
