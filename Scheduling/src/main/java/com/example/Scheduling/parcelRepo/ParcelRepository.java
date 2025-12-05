package com.example.Scheduling.parcelRepo;

import com.example.Scheduling.parcelModels.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {
}