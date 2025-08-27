package com.microservice.driver_service.repository;

import com.microservice.driver_service.Entity.Driver;
import com.microservice.driver_service.ResponseDTO.DriverResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public class DriverCacheRepository {
    private static final String DRIVER_CACHE_PREFIX = "driver:";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void createDriver(Driver savedDriver){
        String Key = DRIVER_CACHE_PREFIX + savedDriver.getDriverId();
        redisTemplate.opsForValue().set(Key, savedDriver);
    }


    public List<Driver> findAll(){
        System.out.println("Cached Bro ");
        Set<String> keys=redisTemplate.keys(DRIVER_CACHE_PREFIX + "*");
        if(keys == null || keys.isEmpty()) return Collections.emptyList();
        List<Object> objects = redisTemplate.opsForValue().multiGet(keys);
        return objects.stream().filter(Objects::nonNull).map(o->(Driver)o).toList();

    }




     public Driver getDriverById(Long driverId){
        String key =DRIVER_CACHE_PREFIX + driverId;

         Object cachedDriver=redisTemplate.opsForValue().get(key);
         if(cachedDriver instanceof Driver){
             Driver driver= (Driver) cachedDriver;
             return  driver;
         }
         return  null;
     }

    public void removeKey(String key) {
        Boolean result = redisTemplate.delete(key);
        if (result) {
            System.out.println("Key deleted: " + key);
        } else {
            System.out.println("Key not found: " + key);
        }
    }
}
