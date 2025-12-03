package com.example.parcelorCourierService.controller;
//
//import com.example.parcelorCourierService.Entity.Parcel;
//import com.example.parcelorCourierService.Entity.dto.ParcelDTO;
//import com.example.parcelorCourierService.Service.ParcelService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/internal/parcels")
//public class ParcelAdminController {
//    private final ParcelService parcelService;
//
//    public ParcelAdminController(ParcelService parcelService) {
//        this.parcelService = parcelService;
//    }
//
//    @PostMapping("/{parcelId}/assign-driver/{driverId}")
//    public ResponseEntity<Void> assignParcelToDriver(
//            @PathVariable Long parcelId,
//            @PathVariable Long driverId
//    ){
//        parcelService.assignDriver(parcelId,driverId);
//        return ResponseEntity.ok().build();
//    }
//
//    @PatchMapping("/{parcelId}/complete-delivery")
//    public  ResponseEntity<String> completeDelivery(@PathVariable Long parcelId){
//        parcelService.markAsDelivered(parcelId);
//        return ResponseEntity.ok("Parcel " + parcelId + " marked as delivered");
//    }
//
//    @GetMapping("/driver/{driverId}")
//    public ResponseEntity<List<ParcelDTO>> getParcelsByDriver(@PathVariable Long driverId){
//        List<ParcelDTO> parcels = parcelService.getParcelsByDriver(driverId);
//        return ResponseEntity.ok(parcels);
//    }
//}
