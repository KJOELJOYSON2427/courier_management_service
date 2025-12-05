package com.example.Scheduling.parcelModels;

public enum ParcelStatus {
    CREATED,   // Just created, no further action yet
    PENDING,   // Waiting for pickup/approval
    ASSIGNED,
    DELIVERED,
    CANCELLED
}