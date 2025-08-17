package com.microservice.driver_service.controller;


import com.microservice.driver_service.Entity.Driver;
import com.microservice.driver_service.RequestDTO.DriverRequest;
import com.microservice.driver_service.ResponseDTO.DriverResponse;
import com.microservice.driver_service.Service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/drivers")
public class DriverController{

    @Autowired
    private DriverService driverService;

    @PostMapping
    public ResponseEntity<DriverResponse> createDriver(@RequestBody DriverRequest driver){
        DriverResponse savedDriver=driverService.createDriver(driver);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDriver);
    }
}
