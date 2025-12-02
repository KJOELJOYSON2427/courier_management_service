package com.example.userService.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private Long id;
    private String email;
    private String fullName;
    private String message;
    private String accessToken;

    public LoginResponse(Long id, String email, String fullName, String message, String accessToken) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.message = message;
        this.accessToken = accessToken;
    }

    // getters and setters
}
