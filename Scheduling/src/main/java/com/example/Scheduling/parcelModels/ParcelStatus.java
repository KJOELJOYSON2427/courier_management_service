package com.example.Scheduling.parcelModels;

public enum ParcelStatus {
    CREATED,    // Just created, no further action yet → sender confirmation email
    PENDING,    // Waiting for pickup/approval
    IN_TRANSIT, // Picked up and en route → recipient "on the way" email
    DELIVERED   // Successfully delivered → final emails to both
}