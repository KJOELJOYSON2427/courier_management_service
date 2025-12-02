package com.microservice.driver_service.openFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name="parcel-service")
public interface ParcelClient {

    @PostMapping("/internal/parcels/{parcelId}/assign-driver/{driverId}")
    void assignParcelToDriver(@PathVariable Long parcelId, @PathVariable Long driverId);

    @GetMapping("/internal/parcels/driver/{driverId}")
    List<ParcelDTO>  getParcelsByDriverId(@PathVariable Long driverId);
}
