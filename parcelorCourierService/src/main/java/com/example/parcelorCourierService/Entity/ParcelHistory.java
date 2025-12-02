package com.example.parcelorCourierService.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "parcel_history")
public class ParcelHistory {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
private String description;
    private  String status;
    private Long parcelGetId;

    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parcel_id")
    private  Parcel parcel;


}
