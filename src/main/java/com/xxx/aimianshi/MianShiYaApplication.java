package com.xxx.aimianshi;

import com.xxx.aimianshi.common.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * MianShiYaApplication
 */
@SpringBootApplication
@EnableWebSecurity
@EnableAspectJAutoProxy
@EnableScheduling
@EnableConfigurationProperties({ JwtProperties.class })
@EnableMethodSecurity
public class MianShiYaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MianShiYaApplication.class, args);
    }
}
