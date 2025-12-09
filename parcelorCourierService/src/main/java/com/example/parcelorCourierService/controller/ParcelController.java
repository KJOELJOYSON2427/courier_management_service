package com.example.parcelorCourierService.controller;


import com.example.parcelorCourierService.Entity.Parcel;
import com.example.parcelorCourierService.Entity.dto.ParcelDTO;
import com.example.parcelorCourierService.Service.ParcelService;
import com.example.parcelorCourierService.request.CreateParcelRequest;
import com.example.parcelorCourierService.request.ParcelStatusUpdateRequest;
import com.example.parcelorCourierService.request.SenderEmailRequest;
import com.example.parcelorCourierService.request.UpdateParcel;
import com.example.parcelorCourierService.response.DeleteResponse;
import com.example.parcelorCourierService.response.ParcelResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parcel")
public class ParcelController {


    @Autowired
    private ParcelService parcelService;

//    //Post /parcels/book
//
//    @PostMapping("/book")
//    public ResponseEntity<ParcelDTO> createParcel(@RequestBody ParcelRequest request){
//        System.out.println("cslllee");
//        ParcelDTO createdParcel = parcelService.createParcelBooking(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdParcel);
//    }
//
//
//    @GetMapping("/customer/{trackingNumber}")
//    public  ResponseEntity<ParcelDTO> getParcelForCustomer(@PathVariable String trackingNumber,@RequestParam Long customerId){
//
//        ParcelDTO dto = parcelService.getParcelByTrackingNumberForCustomer(trackingNumber,customerId);
//        return ResponseEntity.ok(dto);
//
//    }
//
//    @GetMapping("/admin/parcels/{trackingNumber}")
//    public  ResponseEntity<ParcelDTO> getParcelForAdmin(
//            @PathVariable String trackingNumber
//    ){
//        return ResponseEntity.ok(parcelService.getParcelByTrackingNumberForAdmin(trackingNumber));
//    }
//
//// GET /customers/{customerId}/parcels
//    @GetMapping("/customers/{senderId}/parcels")
//    public ResponseEntity<List<ParcelDTO>>  getCustomerParcels(@PathVariable Long senderId){
//
//        return ResponseEntity.ok(parcelService.getParcelByCustomerId(senderId));
//    }



    //---- For Frontend-----
    //ADD PARCEL
    @PostMapping("/")
    public  ResponseEntity<?> createParcel(
                                           @Valid @RequestBody  CreateParcelRequest request){

       try{
           parcelService.createParcel(request);
           return ResponseEntity.ok("Parcel created successfully");
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went Wrong" + e.getMessage());
       }
    }


    //Get All Parcels
    @GetMapping("/")
    public ResponseEntity<?> getAllParcel(@RequestHeader Map<String, String> headers){
        String userId = headers.get("x-user-id");
        String email = headers.get("x-user-email");
        String role = headers.get("x-user-role");
        System.out.println("User with userId" + userId + "UserEmail" +email + " Role " + role);


        // -------- ROLE VALIDATION --------
        if (role == null || !role.equalsIgnoreCase("ADMIN")) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)   // 403
                    .body("Unauthorized: Only ADMIN can access this resource");
        }
        try {
            List<Parcel> parcels = parcelService.getAllParcel();

            if (parcels.isEmpty()) {
                return new ResponseEntity<>("No parcels found", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(parcels, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch parcels: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Update Parcel
    @PutMapping("/{trackingNumber}")
    public ResponseEntity<?> updateParcel(
            @PathVariable String trackingNumber,
            @Valid @RequestBody UpdateParcel parcel)
    {

        try{
            ParcelResponse response = parcelService.updateParcel(parcel, trackingNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Failed to update parcel: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   //Get ONE PARCEL
    @GetMapping("/find/{trackingNumber}")
    public ResponseEntity<?> getOneParcel(
            @PathVariable String trackingNumber
    ){
         try{
             ParcelResponse response = parcelService.getOneParcel(trackingNumber);
             return new ResponseEntity<>(response, HttpStatus.OK);
         }catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch parcel: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get users parcel
    @PostMapping("/me")
    public  ResponseEntity<?> getUsersParcels(@RequestBody SenderEmailRequest request )
    {
        try {
            List<ParcelResponse> parcels = parcelService.getUsersParcelBySenderEmail(request.getEmail());

            if (parcels.isEmpty()) {
                return new ResponseEntity<>("No parcels found for this email", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(parcels, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch parcels: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //delete Parcel

    @DeleteMapping("/{trackingNumber}")
    public  ResponseEntity<?> deleteParcel(@PathVariable String trackingNumber){
        try {
            DeleteResponse response = parcelService.deleteParcel(trackingNumber);
            HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            DeleteResponse response = new DeleteResponse("Failed to delete parcel: " + e.getMessage(), false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/parcel/{trackingNumber}/status-update")
    public ResponseEntity<String> updateParcelState(
            @PathVariable String trackingNumber,
            @RequestBody ParcelStatusUpdateRequest request,
            @RequestHeader Map<String, String> headers

    ) {
        String userId = headers.get("x-user-id");
        String email = headers.get("x-user-email");
        String role = headers.get("x-user-role");
        System.out.println("User with userId" + userId + "UserEmail" +email + " Role " + role);


        // -------- ROLE VALIDATION --------
        if (role == null || !role.equalsIgnoreCase("ADMIN")) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)   // 403
                    .body("Unauthorized: Only ADMIN can access this resource");
        }
        parcelService.updateParcelState(trackingNumber, request);
        return ResponseEntity.ok("Parcel status updated successfully!");
    }

}
