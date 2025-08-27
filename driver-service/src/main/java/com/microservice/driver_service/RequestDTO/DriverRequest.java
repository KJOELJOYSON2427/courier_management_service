package com.microservice.driver_service.RequestDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverRequest {

    private String name;
    private Long driverId;
    private String email;
    private String licenseNumber;
    private String status;
    private String PhoneNumber;
    private String salary;
}
