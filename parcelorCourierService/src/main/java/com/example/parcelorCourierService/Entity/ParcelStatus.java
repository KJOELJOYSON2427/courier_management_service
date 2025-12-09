package com.example.parcelorCourierService.Entity;

public enum ParcelStatus {
    CREATED,   // Just created, no further action yet
    PENDING,    // Waiting for pickup/approval
    IN_TRANSIT, // Picked up and en route → recipient "on the way" email
    DELIVERED   // Suc
}