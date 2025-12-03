package com.example.parcelorCourierService.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteResponse {
    private String message;
    private boolean success;
}