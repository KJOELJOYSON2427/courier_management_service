package com.example.parcelorCourierService.repository;

import com.example.parcelorCourierService.Entity.Parcel;
import com.example.parcelorCourierService.Entity.dto.ParcelDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long>, JpaSpecificationExecutor<Parcel> {

//    @Query("SELECT p FROM Parcel p LEFT JOIN FETCH p.history WHERE p.trackingNumber = :trackingNumber")
//    Optional<Parcel> findByTrackingNumberWithHistory(@Param("trackingNumber") String trackingNumber);
//
//
//    @Query("SELECT p FROM Parcel p where p.senderId = :senderId")
//    List<Parcel> findBySenderId(@Param("senderId") Long senderId);
//
//    Optional<Parcel> findByParcelGetId(Long parcelId);
//
//    List<Parcel> findByDriverId(Long driverId);



    //--------- Frontend ------
    // Custom method to find a parcel by tracking number
    Parcel findByTrackingNumber(String trackingNumber);

    //To get The Parcel By the SenderEmail
    List<Parcel> findBySenderEmail(String email, Sort sort);

    @Query("""
SELECT p.senderId, COUNT(p)
FROM Parcel p
GROUP BY p.senderId
""")
    List<Object[]> countParcelsGroupBySender();
}
