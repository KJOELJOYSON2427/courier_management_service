package com.example.parcelorCourierService.Entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "parcels")
public class Parcel {

//    public Parcel() {
//        this.history = new ArrayList<>();
//        // Default status
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(unique = true, nullable = false)
    private String trackingNumber;

    // REQUIRED SENDER DETAILS
    @Column(nullable = false)
    private String senderName;

    @Column(nullable = false)
    private String senderEmail;

    @Column(nullable = false)
    private String senderAddress;  // NEW FIELD ADDED

    // REQUIRED RECEIVER DETAILS
    @Column(nullable = false)
    private String receiverEmail;

    @Column(nullable = false)
    private String recieverName;

    @Column(nullable = false)
    private String recieverAddress;

    // PARCEL DETAILS
    @Column(nullable = false)
    private double cost;

    private Long senderId;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private String dimensions;

    // TIMESTAMPS
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // STATUS
    @Enumerated(EnumType.STRING)
    private ParcelStatus status = ParcelStatus.CREATED;


    private LocalDateTime pickedUpByCourierAt;

    private LocalDateTime outForDeliveryAt;  // set when driver starts route

    private LocalDateTime deliveredAt;

//    // HISTORY RELATION
//    @OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ParcelHistory> history;
}
