package com.example.userService.controller;

import com.example.userService.Response.ApiResponse;
import com.example.userService.Response.UserResponse;
import com.example.userService.model.User;
import com.example.userService.model.UserWithParcelCountDTO;
import com.example.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
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
//    @GetMapping("/")
//    public ResponseEntity<ApiResponse<List<UserWithParcelCountDTO>>> getUser(){
//        List<UserWithParcelCountDTO> users = userService.getAllUsers();
//        ApiResponse<List<UserWithParcelCountDTO>> response = new ApiResponse<>(
//                users.isEmpty() ? 204 : 200,
//                users.isEmpty() ? "No users found" : "Users retrieved successfully",
//                users
//        );
//        return  ResponseEntity.status(response.getStatus()).body(response);
//    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserWithParcelCountDTO>>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,

            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String email,

            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ){

        Page<UserWithParcelCountDTO> users =
                userService.getAllUsers(page, size, id, email, sortBy, sortDir);

        ApiResponse<Page<UserWithParcelCountDTO>> response = new ApiResponse<>(
                users.isEmpty() ? 204 : 200,
                users.isEmpty() ? "No users found" : "Users retrieved successfully",
                users
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }




}
