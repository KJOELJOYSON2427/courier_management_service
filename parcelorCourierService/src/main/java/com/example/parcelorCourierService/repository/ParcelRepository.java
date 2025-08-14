package com.example.parcelorCourierService.repository;

import com.example.parcelorCourierService.Entity.Parcel;
import com.example.parcelorCourierService.Entity.dto.ParcelDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {

    @Query("SELECT p FROM Parcel p LEFT JOIN FETCH p.history WHERE p.trackingNumber = :trackingNumber")
    Optional<Parcel> findByTrackingNumberWithHistory(@Param("trackingNumber") String trackingNumber);


    @Query("SELECT p FROM Parcel p where p.senderId = :senderId")
    List<Parcel> findBySenderId(@Param("senderId") Long senderId);
}
