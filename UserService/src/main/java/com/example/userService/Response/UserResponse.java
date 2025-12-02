package com.example.userService.Response;

import lombok.Getter;

@Getter
public class UserResponse {
    // getters
    private final Long id;
    private final String email;
    private final String fullName;


    public UserResponse(Long id, String email, String fullName) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
    }

}
