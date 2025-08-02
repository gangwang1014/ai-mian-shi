package com.xxx.aimianshi.common.constant;

import java.util.concurrent.TimeUnit;

public final class RedisKeyManger {
    private RedisKeyManger(){}

    public static final Integer TOKEN_EXPIRE_TIME = 7;

    public static final TimeUnit TIME_UNIT = TimeUnit.DAYS;

    // token:{userId}:{tokenId}
    public static final String TOKEN = "token";

    // user:tokens:{userId}
    public static final String USER_TOKENS = "user:tokens";

    // user:sign:{year}:{userId}
    public static final String USER_SIGN_PREFIX = "user:sign";


    public static String getUserTokenKey(Long userId, String tokenId) {
        return getKey(TOKEN, userId + ":" + tokenId);
    }

    public static String getUserTokenSetKey(Long userId) {
        return getKey(USER_TOKENS, userId.toString());
    }

    public static String getUserSignKey(Integer year, Long userId) {
        return getKey(USER_SIGN_PREFIX, year + ":" + userId);
    }

    private static String getKey(String prefix, String key) {
        return prefix + ":" + key;
    }


}
