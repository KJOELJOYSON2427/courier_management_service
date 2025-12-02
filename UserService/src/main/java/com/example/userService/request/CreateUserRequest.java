package com.example.userService.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String country;

    @NotBlank
    private String address;

    @NotNull
    private Integer age;
}
