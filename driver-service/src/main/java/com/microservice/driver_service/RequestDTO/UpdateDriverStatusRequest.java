package com.microservice.driver_service.RequestDTO;

import com.microservice.driver_service.Entity.DriverStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateDriverStatusRequest {

    private DriverStatus status;


}
