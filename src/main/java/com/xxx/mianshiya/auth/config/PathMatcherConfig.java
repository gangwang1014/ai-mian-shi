package com.xxx.mianshiya.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

@Configuration
public class PathMatcherConfig {

    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }
}