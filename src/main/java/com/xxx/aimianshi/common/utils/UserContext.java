package com.xxx.aimianshi.common.utils;

public class UserContext {
    public static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();

    public static void setCurrentUserId(Long userId) {
        currentUserId.set(userId);
    }

    public static Long getCurrentUserId() {
        return currentUserId.get();
    }

    public static void removeCurrentUserId() {
        currentUserId.remove();
    }
}
