package com.example.gateway.config.security.Manager;

import com.example.gateway.service.JWTService;
import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Configuration
public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTService jwtService;

    public JwtReactiveAuthenticationManager(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
         String token = authentication.getCredentials().toString();

         if(!jwtService.isTokenValid(token)){
             return  Mono.error(new BadCredentialsException("Invalid JWT Token"));
         }

        String email = jwtService.extractEmail(token);
         String role = jwtService.extractRole(token);
         Long id = jwtService.extractId(token);
        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_" + role));

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(email, null, authorities);

        // 5️⃣ Optionally store full claims for downstream use
        auth.setDetails(Map.of(
                "id", id,
                "email", email,
                "role", role
        ));
        return Mono.just(auth);
    }
}
