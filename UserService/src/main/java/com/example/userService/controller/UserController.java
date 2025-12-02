package com.example.userService.controller;

import com.example.userService.Error.ErrorResponse;
import com.example.userService.Response.LoginResponse;
import com.example.userService.Response.UserResponse;
import com.example.userService.request.CreateUserRequest;
import com.example.userService.request.LoginRequest;
import com.example.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserRequest userRequest) {
        try {
            UserResponse user = userService.createUser(userRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(user);

        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Registration failed", ex.getMessage()));
        }
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = userService.loginUser(loginRequest);

        if (loginResponse.getId() != null) {
            // Successful login → HTTP 200 OK
            return ResponseEntity.ok(
                    loginResponse
            );
        } else {
            // Failed login → HTTP 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(
                            loginResponse.getMessage()
                    );
        }
    }

}
