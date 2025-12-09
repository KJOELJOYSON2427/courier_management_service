package com.example.Scheduling.parcelRepo;

import com.example.Scheduling.parcelModels.Parcel;
import com.example.Scheduling.parcelModels.ParcelStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {

    List<Parcel> findByStatusIn(List<ParcelStatus> statuses);
}