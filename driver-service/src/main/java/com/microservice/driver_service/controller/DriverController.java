package com.microservice.driver_service.controller;


import com.microservice.driver_service.Entity.Driver;
import com.microservice.driver_service.RequestDTO.DriverRequest;
import com.microservice.driver_service.RequestDTO.UpdateDriverStatusRequest;
import com.microservice.driver_service.ResponseDTO.DriverResponse;
import com.microservice.driver_service.Service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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


    @GetMapping
    public ResponseEntity<List<DriverResponse>> getAllDrivers(){
         return  ResponseEntity.status(HttpStatus.ACCEPTED).body(driverService.getAllDrivers());
    }


    @PutMapping("/{driverId}")
    public ResponseEntity<DriverResponse> updateDriver(
            @PathVariable Long driverId,
            @RequestBody DriverRequest request
    ) {
        DriverResponse response = driverService.updateDriver(driverId, request);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverResponse> getDriver(@PathVariable Long driverId){
        return ResponseEntity.status(HttpStatus.FOUND).body(driverService.getDriverById(driverId));
    }


    @DeleteMapping("/{driverId}")
    public ResponseEntity<String> deleteDriver(@PathVariable Long driverId) {
            return  ResponseEntity.status(HttpStatus.ACCEPTED).body(driverService.deleteDriver(driverId));

    }

    @PatchMapping("/{driverId}/status")
    public Driver updateStatus(
            @PathVariable Long driverId,
            @RequestBody UpdateDriverStatusRequest request
            ){
        return  driverService.updateDriverStatus(driverId, request.getStatus());
    }
}
