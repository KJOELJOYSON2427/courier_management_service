package com.example.userService.fiegnClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "parcel-service")
public interface ParcelClient {

    @GetMapping("/parcel/sender/counts")
    Map<Long, Long> getParcelCounts();
}