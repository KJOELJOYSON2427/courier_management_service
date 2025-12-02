package com.example.userService.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;  // Default USER

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String address;

    private Integer age;

    private String note;

    private String feedBack;
    @Column(nullable = false)
    private int status = 0;  // Default 0
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;   // Automatically set on insert

    @UpdateTimestamp
    private LocalDateTime updatedAt;   // Automatically updated on update

   public User(){
   }

    public User(String fullName, String password, String email, String country, String address, Integer age) {

        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.country = country;
        this.address = address;
        this.age = age;
    }
}

