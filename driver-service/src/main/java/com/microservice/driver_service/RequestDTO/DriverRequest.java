package com.microservice.driver_service.RequestDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverRequest {

    private String name;
    private String licenseNumber;
    private String status;
    private String PhoneNumber;
    private String salary;
}
