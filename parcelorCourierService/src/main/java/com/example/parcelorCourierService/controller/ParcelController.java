package com.example.parcelorCourierService.controller;


import com.example.parcelorCourierService.Entity.dto.ParcelDTO;
import com.example.parcelorCourierService.Service.ParcelService;
import com.example.parcelorCourierService.request.ParcelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ParcelController {


    @Autowired
    private ParcelService parcelService;

    //Post /parcels/book

    @PostMapping("/book")
    public ResponseEntity<ParcelDTO> createParcel(@RequestBody ParcelRequest request){
        System.out.println("cslllee");
        ParcelDTO createdParcel = parcelService.createParcelBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParcel);
    }


    @GetMapping("/customer/{trackingNumber}")
    public  ResponseEntity<ParcelDTO> getParcelForCustomer(@PathVariable String trackingNumber,@RequestParam Long customerId){

        ParcelDTO dto = parcelService.getParcelByTrackingNumberForCustomer(trackingNumber,customerId);
        return ResponseEntity.ok(dto);

    }

    @GetMapping("/admin/parcels/{trackingNumber}")
    public  ResponseEntity<ParcelDTO> getParcelForAdmin(
            @PathVariable String trackingNumber
    ){
        return ResponseEntity.ok(parcelService.getParcelByTrackingNumberForAdmin(trackingNumber));
    }

// GET /customers/{customerId}/parcels
    @GetMapping("/customers/{senderId}/parcels")
    public ResponseEntity<List<ParcelDTO>>  getCustomerParcels(@PathVariable Long senderId){

        return ResponseEntity.ok(parcelService.getParcelByCustomerId(senderId));
    }



}
