package com.xxx.aimianshi.auth.security;

import com.xxx.aimianshi.common.client.JwtClient;
import com.xxx.aimianshi.common.exception.TokenAuthenticationException;
import com.xxx.aimianshi.permission.mapper.PermissionMapper;
import com.xxx.aimianshi.userrole.mapper.UserRoleMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtClient jwtClient;

    private final PermissionMapper permissionMapper;

    private final UserRoleMapper userRoleMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        String token = authenticationToken.getCredentials().toString();
        try {
            Claims claims = jwtClient.parseJWT(token);
            return Optional.ofNullable(claims)
                    .map(claim -> {
//                        String userId = claim.getSubject();
                        Long userId = claim.get("userId", Long.class);
                        log.info("userId: {}", userId);
                        // 加载用户权限
                        List<String> Permissions = loadUserPermissions(userId);
                        log.info("用户权限 Permissions: {}", Permissions);

                        // 加载用户角色
                        List<String> roles = loadUserRoles(userId);

                        // 构造权限对象
                        List<GrantedAuthority> authorities = Permissions.stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());
                        authorities.addAll(roles.stream()
                                // 加ROLE_前缀
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                .toList());

                        log.info("用户权限对象 authorities: {}", authorities);
                        return new JwtAuthenticationToken(userId, token, authorities);
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

    private List<String> loadUserPermissions(Long userId) {
        return permissionMapper.findPermissionsByUserId(userId);
    }

    private List<String> loadUserRoles(Long userId) {
        return userRoleMapper.getUserRoles(userId);
    }
}