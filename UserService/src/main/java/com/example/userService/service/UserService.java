package com.example.userService.service;

import com.example.userService.Response.LoginResponse;
import com.example.userService.Response.UserResponse;
import com.example.userService.model.User;
import com.example.userService.repository.UserRepository;
import com.example.userService.request.CreateUserRequest;
import com.example.userService.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserResponse createUser(CreateUserRequest req){

        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setCountry(req.getCountry());
        user.setAddress(req.getAddress());
        user.setAge(req.getAge());

        // Hash password
        String hashedPassword = passwordEncoder.encode(req.getPassword());
        user.setPassword(hashedPassword);

        // Save user
        User userRes=null;
        try{
             userRes=userRepository.save(user);
        }catch (Exception e){
            System.out.println("The User Creation Error in the Server");
        }

        assert userRes != null;
        return new UserResponse(userRes.getId(), userRes.getEmail(), user.getFullName());
    }

    @Bean
    CommandLineRunner init(UserRepository repo) {
        return args -> {
            if (!repo.existsByEmail("parcel@joel")) {

                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                User user = new User(
                        "parcel@joel",
                        encoder.encode("yourPassword123"),
                        "parcel@joel",
                        "india",
                        "Some address",
                        22
                );

                repo.save(user);
                System.out.println("Test user created: parcel@joel");
            }
        };
    }


    public LoginResponse loginUser(LoginRequest loginRequest) {

        try {
            // 1. Find user by email
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("Invalid email or password"));

            // 2. Validate password
            boolean isPasswordCorrect = passwordEncoder.matches(
                    loginRequest.getPassword(),       // raw password
                    user.getPassword()                // encoded password from DB
            );

            if (!isPasswordCorrect) {
                throw new RuntimeException("Invalid email or password");
            }

            // 3. Generate JWT token
            String token = jwtService.generateToken(user);
            // 4. Return success response
            return new LoginResponse(
                    user.getId(),
                    user.getEmail(),
                    user.getFullName(),
                    "Login successful",
                    token
            );

        } catch (Exception e) {
            return new LoginResponse(null, null, null, e.getMessage(), null);
        }
    }

}
