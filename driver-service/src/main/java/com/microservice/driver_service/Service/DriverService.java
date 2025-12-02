package com.microservice.driver_service.Service;

import com.microservice.driver_service.Entity.Driver;
import com.microservice.driver_service.Entity.DriverStatus;
import com.microservice.driver_service.RequestDTO.DriverRequest;
import com.microservice.driver_service.ResponseDTO.DriverResponse;
import com.microservice.driver_service.openFeign.ParcelClient;
import com.microservice.driver_service.openFeign.ParcelDTO;
import com.microservice.driver_service.repository.DriverCacheRepository;
import com.microservice.driver_service.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DriverService {
@Autowired
private final ParcelClient parcelClient;
    private final RedisTemplate<String, Object> redisTemplate;
      @Autowired
     private final DriverMapper driverMapper;
      @Autowired
     private final DriverRepository driverRepository;
@Autowired
private final DriverCacheRepository redis;
    private static final String DRIVER_CACHE_PREFIX = "driver:";
    public DriverResponse createDriver(DriverRequest request){
        Driver driver =driverMapper.toEnity(request);
        Driver savedDriver = driverRepository.save(driver);

        //Save in Redis Cache
         redis.createDriver(savedDriver);


        return driverMapper.toResponse(savedDriver);
    }



    public List<DriverResponse> getAllDrivers(){

         List<Driver> cached = redis.findAll();
         if(!cached.isEmpty()){
             return  cached.stream().
                     map(driverMapper::toResponse)
                     .toList();
         }

        List<Driver> dbDrivers = driverRepository.findAll();
         dbDrivers.forEach(redis::createDriver);
        return dbDrivers.stream().filter(Objects::nonNull).map(driverMapper::toResponse).toList();
    }


    public DriverResponse getDriverById(Long driverId) {
        String key = DRIVER_CACHE_PREFIX + driverId;
        Driver cachedDriver = redis.getDriverById(driverId);
        if(cachedDriver!=null){
            return driverMapper.toResponse(cachedDriver);
        }

        // 2. If not cached → fetch from Postgresd
        Driver driver = driverRepository.findByDriverId(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        // 3. Save in Redis with TTL
        redisTemplate.opsForValue().set(key, driver, 10, TimeUnit.MINUTES);
        return driverMapper.toResponse(driver);
    }

    public DriverResponse updateDriver(Long driverId, DriverRequest request){

        Driver driver = driverRepository.findByDriverId(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found with id: " + driverId));
  // 2. Update fields
        driver.setName(request.getName());
        driver.setLicenseNumber(request.getLicenseNumber());
        driver.setPhoneNumber(request.getPhoneNumber());
        driver.setEmail(request.getEmail());
        driver.setSalary(Double.valueOf(request.getSalary()));
        driver.setStatus(DriverStatus.valueOf(request.getStatus()));
        Driver updatedDriver = driverRepository.save(driver);

        // 4. Update cache
        String cacheKey = DRIVER_CACHE_PREFIX + updatedDriver.getDriverId();
        redisTemplate.opsForValue().set(cacheKey,updatedDriver);

        return driverMapper.toResponse(driver);

    }
   @Transactional
    public String deleteDriver(long driverId){
        // Build the Redis key
        String key = DRIVER_CACHE_PREFIX + driverId;

        // Check if driver exists
        Optional<Driver> driver = driverRepository.findByDriverId(driverId);
        if(driver.isEmpty()){
            return  "Driver not found";
        }

        // Delete driver from Postgres
        driverRepository.deleteByDriverId(driverId);

        //Remove from Redis cache
       redis.removeKey(key);

        return "Driver deleted successfully";
    }



    @Transactional
    public Driver updateDriverStatus(Long driverId, DriverStatus newStatus){
        Driver driver = driverRepository.findByDriverId(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found on update"));

        driver.setStatus(newStatus);
        Driver updated = driverRepository.save(driver);
        String key = DRIVER_CACHE_PREFIX + driverId;
        redisTemplate.opsForValue().set(key,updated);
        return updated;

    }


    @Transactional
    public DriverResponse assignParcelsToDriver(Long driverId, List<Long> parcelIds){
        Driver driver =  driverRepository.findByDriverId(driverId)
                .orElseThrow(()-> new RuntimeException("Driver not found"));

        if(driver.getStatus() == DriverStatus.OFFLINE){
            throw new RuntimeException("Driver is offline");
        }
        for(Long parcelId : parcelIds){
            parcelClient.assignParcelToDriver(parcelId, driverId);

        }

        driver.setStatus(DriverStatus.ON_DELIVERY);
        Driver savedDriver = driverRepository.save(driver);
        DriverResponse response = driverMapper.toResponse(savedDriver);
        redisTemplate.opsForValue().set("driver:" + driverId, response);
        return driverMapper.toResponse(savedDriver);
    }


    public List<ParcelDTO> getDriverParcels(Long driverId){
        String key = "driver:parcels:" + driverId;
        List<ParcelDTO> cachedParcels=(List<ParcelDTO>) redisTemplate.opsForValue().get(key);
        if(cachedParcels !=null){
            System.out.println("Returning parcels from Redis cache...");
            return cachedParcels;
        }
        // 2. If not in Redis, fetch from Parcel Service
        System.out.println("Fetching parcels from Parcel Service...");
        List<ParcelDTO> parcels = parcelClient.getParcelsByDriverId(driverId);
        // 3. Store in Redis with TTL (optional)
        redisTemplate.opsForValue().set(key, parcels, 5, TimeUnit.MINUTES);
        return parcels;
    }
}
