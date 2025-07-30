package com.xxx.mianshiya.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.mianshiya.common.domain.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TokenAuthenticationHandler implements AccessDeniedHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        Result.error(401, accessDeniedException.getMessage())
                )
        );
    }
}