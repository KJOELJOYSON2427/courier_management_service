package com.microservice.driver_service.repository;

import com.microservice.driver_service.Entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
