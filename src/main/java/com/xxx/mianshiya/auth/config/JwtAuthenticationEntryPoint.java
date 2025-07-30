package com.xxx.mianshiya.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.mianshiya.common.domain.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Resource
    private ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");

        response.getWriter().write(
                objectMapper.writeValueAsString(
                        Result.error(401, authException.getMessage())
                )
        );
    }
}