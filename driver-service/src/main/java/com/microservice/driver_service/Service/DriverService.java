package com.microservice.driver_service.Service;

import com.microservice.driver_service.Entity.Driver;
import com.microservice.driver_service.RequestDTO.DriverRequest;
import com.microservice.driver_service.ResponseDTO.DriverResponse;
import com.microservice.driver_service.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverService {


     private final DriverMapper driverMapper;
     private final DriverRepository driverRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String DRIVER_CACHE_PREFIX = "driver:";

    public DriverResponse createDriver(DriverRequest request){
        Driver driver =driverMapper.toEnity(request);
        Driver savedDriver = driverRepository.save(driver);

        //Save in Redis Cache

        String Key = DRIVER_CACHE_PREFIX + savedDriver.getId();
        redisTemplate.opsForValue().set(Key, savedDriver);

        return driverMapper.toResponse(savedDriver);
    }
}
