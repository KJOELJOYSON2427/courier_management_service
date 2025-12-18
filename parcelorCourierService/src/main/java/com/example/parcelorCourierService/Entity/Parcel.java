package com.example.parcelorCourierService.Entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

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



    @UuidGenerator
    @Column(name = "tracking_number", unique = true, nullable = false)
    private String trackingNumber;

    @Column(name = "sender_name", nullable = false)
    private String senderName;

    @Column(name = "sender_email", nullable = false)
    private String senderEmail;

    @Column(name = "sender_address", nullable = false)
    private String senderAddress;

    // REQUIRED RECEIVER DETAILS
    @Column(name = "receiver_email",nullable = false)
    private String receiverEmail;

    @Column(name = "reciever_name", nullable = false)
    private String recieverName;

    @Column(name = "reciever_address",nullable = false)
    private String recieverAddress;

    // PARCEL DETAILS
    @Column(nullable = false)
    private double cost;
    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private String dimensions;

    // TIMESTAMPS
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    private String note;
    // STATUS
    @Enumerated(EnumType.STRING)
    private ParcelStatus status = ParcelStatus.CREATED;
// Add these fields to your Parcel class

    @Column(name = "picked_up_by_courier_at")
    private LocalDateTime pickedUpByCourierAt;

    @Column(name = "out_for_delivery_at")
    private LocalDateTime outForDeliveryAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
    @Column(name = "created_email_sent")
    private Boolean  createdEmailSent = false;

    @Column(name = "picked_up_email_sent")
    private Boolean  pickedUpEmailSent = false;

    @Column(name = "out_for_delivery_email_sent")
    private Boolean  outForDeliveryEmailSent = false;

    @Column(name = "delivered_email_sent")
    private Boolean  deliveredEmailSent = false;

//    // HISTORY RELATION
//    @OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ParcelHistory> history;


}
