package com.example.userService.repository;

import com.example.userService.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
//    Page<User> findByIdAndEmailContainingIgnoreCase(
//            Long id,
//            String email,
//            Pageable pageable
//    );
//
//    Page<User> findByEmailContainingIgnoreCase(
//            String email,
//            Pageable pageable
//    );
//    Page<User> findByIdContainingIgnoreCase(
//            Long id,
//            Pageable pageable
//    );
}
