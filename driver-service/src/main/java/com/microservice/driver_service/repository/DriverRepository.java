package com.microservice.driver_service.repository;

import com.microservice.driver_service.Entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByDriverId(Long driverId);

    void deleteByDriverId(Long driverId);
}
