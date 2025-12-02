package com.microservice.driver_service.controller;

import com.microservice.driver_service.RequestDTO.ParcelAssignRequest;
import com.microservice.driver_service.ResponseDTO.DriverResponse;
import com.microservice.driver_service.Service.DriverService;
import com.microservice.driver_service.openFeign.ParcelDTO;
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
             @RequestBody ParcelAssignRequest parcelAssignRequest
             ){
         DriverResponse driver = driverService.assignParcelsToDriver(driverId, parcelAssignRequest.getParcelId());
         return ResponseEntity.ok(driver);
     }

    @GetMapping("/{driverId}/parcels")
    public ResponseEntity<List<ParcelDTO>> getDriverParcels(@PathVariable Long driverId){
         List<ParcelDTO> parcels=driverService.getDriverParcels(driverId);
         return ResponseEntity.ok(parcels);
    }

}
