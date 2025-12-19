package com.example.userService.repository.Specification;

import com.example.userService.model.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class UserSpecification {

    public static Specification<User> filter(Long id, String email) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Filter by ID (exact match)
            if (id != null) {
                predicates.add(cb.equal(root.get("id"), id));
            }

            // Filter by Email (case-insensitive LIKE)
            if (email != null && !email.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("email")),
                                "%" + email.toLowerCase() + "%"
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
