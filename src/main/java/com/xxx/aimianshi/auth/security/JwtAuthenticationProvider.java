package com.xxx.aimianshi.auth.security;

import com.xxx.aimianshi.common.client.JwtClient;
import com.xxx.aimianshi.common.exception.TokenAuthenticationException;
import com.xxx.aimianshi.common.utils.UserContext;
import com.xxx.aimianshi.permission.mapper.PermissionMapper;
import com.xxx.aimianshi.user.constant.UserConstant;
import com.xxx.aimianshi.user.repository.TokenRepository;
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

    private final TokenRepository tokenRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        String token = authenticationToken.getCredentials().toString();
        try {
            Claims claims = jwtClient.parseJWT(token);
            return Optional.ofNullable(claims)
                    .map(claim -> {
                        Long userId = claim.get(UserConstant.USER_ID, Long.class);
                        String tokenId = claim.get(UserConstant.TOKEN_ID, String.class);

                        // 查看是否是被清除的 token
                        if (!checkTokenExist(userId, tokenId)) {
                            throw new TokenAuthenticationException("Token not exist");
                        }

                        // 设置用户上下文信息
                        setUserContext(userId, tokenId);
                        // 加载用户权限

                        List<GrantedAuthority> authorities = getUserGrantedAuthorities(userId);
                        return new JwtAuthenticationToken(userId, token, authorities);
                    })
                    .orElseThrow(() -> new TokenAuthenticationException("Invalid token: "));
        } catch (Exception e) {
            throw new TokenAuthenticationException("Invalid token: " + e.getMessage());
        }
    }

    private void setUserContext(Long userId, String tokenId) {
        UserContext.setCurrentUserId(userId);
        log.info("userId: {}", userId);
        UserContext.setCurrentUserTokenId(tokenId);
        log.info("tokenId: {}", tokenId);
    }

    private boolean checkTokenExist(Long userId, String tokenId) {
        return tokenRepository.getUserToken(userId, tokenId) != null;
    }

    private List<GrantedAuthority> getUserGrantedAuthorities(Long userId) {
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
        return authorities;
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