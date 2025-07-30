package com.xxx.mianshiya.auth.config;

import com.xxx.mianshiya.auth.security.JwtAuthenticationToken;
import com.xxx.mianshiya.auth.security.SecurityWhiteList;
import com.xxx.mianshiya.common.constant.TokenConstant;
import com.xxx.mianshiya.common.exception.TokenAuthenticationException;
import com.xxx.mianshiya.common.properties.JwtProperties;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final SecurityWhiteList securityWhiteList;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 判断 是否在 系统白名单 whitelist
        String requestURI = request.getRequestURI();
        if (isWhitelisted(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = getToken(request);
            JwtAuthenticationToken authenticationToken = convert(token);
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            throw e;
//            jwtAuthenticationEntryPoint.commence(request, response, e);
//            return;
        }
        filterChain.doFilter(request, response);
    }

    private JwtAuthenticationToken convert(String token) {
        return new JwtAuthenticationToken(token);
    }

    private boolean isWhitelisted(String requestURI) {
        return securityWhiteList.isWhiteList(requestURI);
    }

    private @Nullable String getToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(jwtProperties.getTokenName()))
                .map(this::extractBearerToken)
                .orElseThrow(() -> new TokenAuthenticationException("Token does not exist"));
    }

    private String extractBearerToken(String token) {
        if (token.startsWith(TokenConstant.TOKEN_PREFIX)) {
            return token.substring(TokenConstant.TOKEN_PREFIX.length());
        }
        throw new TokenAuthenticationException("Token prefix does not match");
    }

}