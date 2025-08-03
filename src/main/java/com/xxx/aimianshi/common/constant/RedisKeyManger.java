package com.xxx.aimianshi.common.constant;

import java.util.concurrent.TimeUnit;

public final class RedisKeyManger {
    private RedisKeyManger(){}

    public static final Integer TOKEN_EXPIRE_TIME = 30;

    public static final TimeUnit TIME_UNIT = TimeUnit.DAYS;

    // token:{userId}:{tokenId}
    public static final String TOKEN = "token";

    // user:tokens:{userId}
    public static final String USER_TOKENS = "user:tokens";

    // user:sign:{year}:{userId}
    public static final String USER_SIGN = "user:sign";

    // bank:question:{questionBankId}
    public static final String BANK_QUESTION = "bank:question";


    public static String getUserTokenKey(Long userId, String tokenId) {
        return getKey(TOKEN, userId + ":" + tokenId);
    }

    public static String getUserTokenSetKey(Long userId) {
        return getKey(USER_TOKENS, userId.toString());
    }

    public static String getUserSignKey(Integer year, Long userId) {
        return getKey(USER_SIGN, year + ":" + userId);
    }

    public static String getBankQuestionKey(Long questionBankId) {
        return getKey(BANK_QUESTION, questionBankId.toString());
    }

    private static String getKey(String prefix, String key) {
        return prefix + ":" + key;
    }


}
