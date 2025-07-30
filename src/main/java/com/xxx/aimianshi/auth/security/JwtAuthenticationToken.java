package com.xxx.aimianshi.auth.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private String jwt;

    public JwtAuthenticationToken(String jwt) {
        super(null);
        this.principal = null;
        this.jwt = jwt;
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(Object principal, String jwt,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.jwt = jwt;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.jwt = null;
    }
}