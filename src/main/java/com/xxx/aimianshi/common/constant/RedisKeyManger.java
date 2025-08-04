package com.xxx.aimianshi.common.constant;

import java.util.concurrent.TimeUnit;

public final class RedisKeyManger {
    private RedisKeyManger(){}

    public static final Long TOKEN_EXPIRE_TIME = 30L;

    public static final TimeUnit TIME_UNIT = TimeUnit.DAYS;

    public static final Long QUESTION_EXPIRE_TIME = 7L;

    // token:{userId}:{tokenId}
    public static final String TOKEN = "token";

    // user:tokens:{userId}
    public static final String USER_TOKENS = "user:tokens";

    // user:sign:{year}:{userId}
    public static final String USER_SIGN = "user:sign";

    // bank:question:{questionBankId}
    public static final String BANK_QUESTION = "bank:question";

    // chat:memory:{conversationId}
    public static final String CHAT_MEMORY = "chat:memory";

    public static final Long CHAT_MEMORY_EXPIRE_TIME = 7L;

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


    public static String getChatMemoryKey(String conversationId) {
        return getKey(CHAT_MEMORY, conversationId);
    }
}
