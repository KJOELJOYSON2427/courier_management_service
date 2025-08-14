package com.microservice.driver_service.Service;

import com.microservice.driver_service.Entity.Driver;
import com.microservice.driver_service.Entity.DriverStatus;
import com.microservice.driver_service.RequestDTO.DriverRequest;
import com.microservice.driver_service.ResponseDTO.DriverResponse;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {

    public Driver toEnity(DriverRequest request){
        return Driver.builder()
                .name(request.getName())
                .licenseNumber(request.getLicenseNumber())
                .status(DriverStatus.valueOf(request.getStatus().toUpperCase()))
                .phoneNumber(request.getPhoneNumber())
                .salary(Double.valueOf(request.getSalary()))
                .build();
    }



    public DriverResponse toResponse(Driver driver){
        return  DriverResponse.builder()
                .name(driver.getName())
                .licenseNumber(driver.getLicenseNumber())
                .PhoneNumber(driver.getPhoneNumber())
                .status(driver.getStatus().name())
                .salary(String.valueOf(driver.getSalary()))
                .build();

    }
}
