package com.xxx.mianshiya.auth.security;

import com.xxx.mianshiya.common.client.JwtClient;
import com.xxx.mianshiya.common.exception.TokenAuthenticationException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class
JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtClient jwtClient;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        String token = authenticationToken.getCredentials().toString();
        try {
            Claims claims = jwtClient.parseJWT(token);
            return Optional.ofNullable(claims)
                    .map(c -> {
                        String userId = c.getSubject();
                        return new JwtAuthenticationToken(userId, token, null);
                    })
                    .orElseThrow(() -> new TokenAuthenticationException("Invalid token"));
        } catch (Exception e) {
            throw new TokenAuthenticationException("Invalid token" + e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}