package com.example.gateway.config.security.filters;


import com.example.gateway.config.security.Manager.JwtReactiveAuthenticationManager;
import com.example.gateway.config.security.convertor.JwtServerAuthenticationConverter;
import com.example.gateway.config.security.headerFilter.JwtHeaderForwardFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

/*

create a security configuration class and enable Spring Security for your gateway using the @EnableWebFluxSecurity annotation. In this class, you’ll customize the SecurityWebFilterChain to:

Register custom filters (like your JWT authentication filter)
Define which URIs should be secured
Allow public access to routes like login, registration, or actuator endpoints
Let’s break this down step by step by first creating the necessary building blocks to configure the SecurityWebFilterChain.
 */


/*
AuthenticationWebFilter with a ReactiveAuthenticationManager
Key Characteristics:
You implement a custom ReactiveAuthenticationManager that:
Validates the JWT token
Returns an Authentication object (like UsernamePasswordAuthenticationToken)
Spring Security manages: Filter ordering, Error handling, Security context population, Integration with annotations like @PreAuthorize
 */
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
    public SecurityConfig() {
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         AuthenticationWebFilter jwtAuthenticationWebFilter,
                                                         JwtHeaderForwardFilter headerForwardFilter) {
         return http
                 .csrf(
                         ServerHttpSecurity.CsrfSpec :: disable
                 )
                 .authorizeExchange(
                         exchanges -> exchanges
                                 .pathMatchers("*/auth/**").permitAll()
                                 .anyExchange().authenticated()
                 ).securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                 .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                 // 2️⃣ Add your header-forwarding filter after authentication
                 .addFilterAfter(headerForwardFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                 .httpBasic(ServerHttpSecurity.HttpBasicSpec :: disable)
                 .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                 .build();
    }


    @Bean
    public AuthenticationWebFilter jwtAuthenticationWebFilter(
            JwtReactiveAuthenticationManager authManager,
            JwtServerAuthenticationConverter converter
    ){
         AuthenticationWebFilter filter = new AuthenticationWebFilter(authManager);
         filter.setServerAuthenticationConverter(converter);
         return  filter;

    }
}