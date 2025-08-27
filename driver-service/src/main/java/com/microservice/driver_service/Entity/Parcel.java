package com.microservice.driver_service.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parcel_to_driver")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parcelId;
}
