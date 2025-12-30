package com.thehecklers.planefinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .authorizeExchange(exchange -> exchange
                .pathMatchers("/aircraft/**").hasAuthority("SCOPE_closedid")
                .pathMatchers("/aircraftadmin/**").hasAuthority("SCOPE_openid")
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtSpec -> {}));

        return http.build();
    }

}
