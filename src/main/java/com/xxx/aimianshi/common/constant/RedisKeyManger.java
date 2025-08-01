package com.xxx.aimianshi.common.constant;

public final class RedisKeyManger {
    private RedisKeyManger(){}

    public static final String USER_ID = "user:id";
    public static final String USER_SIGN_PREFIX = "user:sign";


    public static String getUserIdKey(Long userId) {
        return getKey(USER_ID, userId.toString());
    }

    public static String getUserSignKey(Integer year, Long userId) {
        return getKey(USER_SIGN_PREFIX, year + ":" + userId);
    }

    private static String getKey(String prefix, String key) {
        return prefix + ":" + key;
    }


}
