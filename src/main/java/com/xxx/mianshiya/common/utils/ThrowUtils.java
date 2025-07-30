package com.xxx.mianshiya.common.utils;

import com.xxx.mianshiya.common.exception.BizException;

public class ThrowUtils {

    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    public static void throwIf(boolean condition, String msg) {
        throwIf(condition, new BizException(msg));
    }
}
