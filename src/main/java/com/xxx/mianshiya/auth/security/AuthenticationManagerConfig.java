package com.xxx.mianshiya.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
    }
}