package com.xxx.aimianshi.auth.security;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

@Component
public class SecurityWhiteList {
    private final AntPathMatcher matcher;

    public SecurityWhiteList(AntPathMatcher matcher) {
        this.matcher = matcher;
    }

    public static final String [] WHITE_LIST = {
            "/user/login",
            "/user/register",
            "/druid/**",
    };

    public boolean isWhiteList(String path) {
        return Arrays.stream(WHITE_LIST).anyMatch(pattern -> matcher.match(pattern, path));
    }
}