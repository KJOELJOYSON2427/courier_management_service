package com.example.parcelorCourierService.Entity;

public enum ParcelStatus {
    CREATED,   // Just created, no further action yet
    PENDING,   // Waiting for pickup/approval
    IN_TRANSIT,
    DELIVERED,
    CANCELLED
}