package com.example.gateway.config.security.headerFilter;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;



/*
Original exchange
      │
   mutate() → Builder
      │
   request(lambda) → Builder
      │    └─ Spring executes your lambda here
      │          └─ headers(lambda) → adds headers
      │
    build() → new ServerWebExchange
      │
    Downstream filter receives new exchange

 */
@Component
public class JwtHeaderForwardFilter  implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> {

                    var auth = securityContext.getAuthentication();

                    if(auth != null && auth.isAuthenticated() && auth.getDetails() instanceof Map claims){
                        // Mutate the request to add headers
                        ServerWebExchange mutatedExchange = exchange.mutate()
                                .request(r -> r.headers(headers -> {
                                    headers.add("X-User-Id", claims.get("id").toString());
                                    headers.add("X-User-Email", claims.get("email").toString());
                                    headers.add("X-User-Role", claims.get("role").toString());
                                }))
                                .build();

                        return chain.filter(mutatedExchange);


                    }
                    // If not authenticated, continue without mutation

                    return  chain.filter(exchange);
                })
                .switchIfEmpty(chain.filter(exchange));
    }
}
