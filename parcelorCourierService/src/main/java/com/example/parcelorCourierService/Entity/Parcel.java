package com.example.parcelorCourierService.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@Table(name = "parcels")
public class Parcel {
    public Parcel() {
        this.history = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String trackingNumber;// NEW FIELD
    private Long senderId;
    private String recieverName;
    private String recieverAddress;
    private double weight;
    private String dimensions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private ParcelStatus status;

    private Long driverId;

    // Relation with history
    @OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParcelHistory> history;


}
