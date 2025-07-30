package com.xxx.mianshiya;

import com.xxx.mianshiya.common.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * MianShiYaApplication
 */
@SpringBootApplication
@EnableWebSecurity
@EnableAspectJAutoProxy
@EnableConfigurationProperties({ JwtProperties.class })
public class MianShiYaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MianShiYaApplication.class, args);
    }
}
