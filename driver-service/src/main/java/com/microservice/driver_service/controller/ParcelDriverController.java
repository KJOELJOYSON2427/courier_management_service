package com.microservice.driver_service.controller;

import com.microservice.driver_service.ResponseDTO.DriverResponse;
import com.microservice.driver_service.Service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/drivers")
public class ParcelDriverController {
    @Autowired
    private DriverService driverService;
     @PostMapping("/assign-parcels")
    public ResponseEntity<DriverResponse> assignParcelsToDriver(
             @RequestParam Long driverId,
             @RequestBody List<Long> parcelIds
             ){
         DriverResponse driver = driverService.assignParcelsToDriver(driverId, parcelIds);
         return ResponseEntity.ok(driver);
     }
}
