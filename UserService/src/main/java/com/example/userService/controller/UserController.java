package com.example.userService.controller;

import com.example.userService.Response.ApiResponse;
import com.example.userService.Response.UserResponse;
import com.example.userService.model.User;
import com.example.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //Deleting the User
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Get all the users
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getUser(){
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> response = new ApiResponse<>(
                users.isEmpty() ? 204 : 200,
                users.isEmpty() ? "No users found" : "Users retrieved successfully",
                users
        );
        return  ResponseEntity.status(response.getStatus()).body(response);
    }
}
