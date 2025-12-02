package com.example.userService.Response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    // Constructor, getters, setters
    public ApiResponse(int status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
