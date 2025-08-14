package com.microservice.driver_service.ResponseDTO;



import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverResponse {
    private String name;
    private String licenseNumber;
    private String status;
    private String PhoneNumber;
    private String salary;
}
