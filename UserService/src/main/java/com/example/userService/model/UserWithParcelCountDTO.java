package com.example.userService.model;

public record UserWithParcelCountDTO(
        Long id,
        String fullName,
        String email,
        Long parcelCount
) {}
