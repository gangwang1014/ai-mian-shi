package com.xxx.aimianshi.user.constant;

public final class UserConstant {
    private UserConstant() {
        throw new AssertionError("Cannot instantiate");
    }

    public static final int TEMP_NICKNAME_LENGTH = 7;

    public static final String TEMP_NICKNAME_PRE = "user_";

    public static final String USER_PASSWORD_PATTEN =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*])[^\\s]{8,16}$";

    public static final String USER_ID = "userId";

    public static final String TOKEN_ID = "tokenId";

    public static final String TOKEN_VERSION = "version";

    public static final Integer MAX_LOGIN_COUNT = 3;
}
