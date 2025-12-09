package com.example.parcelorCourierService.request;

import lombok.Data;

@Data
public class ParcelStatusUpdateRequest {

    private Boolean pickedUp;         // true = mark picked up
    private Boolean outForDelivery;   // true = mark out for delivery
    private Boolean delivered;        // true = mark delivered
}
