package com.example.gateway.config.security.Routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {


    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {

        return builder.routes()

                // 🚚 Parcel Service Route
                .route("parcel-service", r -> r
                        .path("/api/v1/parcel/**")
                        .filters(f -> f.stripPrefix(2))  // remove /api/v1
                        .uri("lb://parcel-service")
                )

                // 👤 Auth / User Service Route
                .route("user-service", r -> r
                        .path("/api/v1/auth/**" , "/api/v1/user/**")

                        .filters(f -> f.stripPrefix(2))  // remove /api/v1
                        .uri("lb://user-service")
                )
                // 👤 Auth / User Service Route
                .route("BackendServices", r -> r
                        .path("/api/v1/gmail/**")
                        .filters(f -> f.stripPrefix(2))  // remove /api/v1
                        .uri("lb://BackendServices")
                )
                .build();
    }

}