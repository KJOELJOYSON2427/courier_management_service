package com.example.userService.Error;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



public class ErrorResponse {
    private final String message;
    private final String details;

    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() { return message; }
    public String getDetails() { return details; }
}
