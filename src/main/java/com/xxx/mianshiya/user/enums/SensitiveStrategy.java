package com.xxx.mianshiya.user.enums;

import java.util.function.Function;

public enum SensitiveStrategy {
    /**
     * 身份证号脱敏：显示前4位和后4位，其余用 * 号隐藏
     * 示例：110105198001011234 → 1101********1234
     */
    ID_CARD(s -> safeHandle(s, "(\\d{4})\\d{10}(\\w{4})", "$1********$2")),

    /**
     * 邮箱脱敏：保留用户名首字符和域名
     * 示例：johndoe@example.com → j*****@example.com
     */
    EMAIL(s -> {
        if (s == null || !s.contains("@")) return s;
        int atIndex = s.indexOf("@");
        if (atIndex <= 1) return "*".repeat(atIndex) + s.substring(atIndex);
        return s.charAt(0) + "*".repeat(atIndex - 1) + s.substring(atIndex);
    }),

    /**
     * 手机号脱敏：保留前3位和后4位
     * 示例：13812345678 → 138****5678
     */
    PHONE(s -> safeHandle(s, "(\\d{3})\\d{4}(\\d{4})", "$1****$2"));

    private final Function<String, String> desensitize;

    SensitiveStrategy(Function<String, String> desensitize) {
        this.desensitize = desensitize;
    }

    public Function<String, String> desensitize() {
        return desensitize;
    }

    private static String safeHandle(String input, String pattern, String replacement) {
        if (input == null || input.isEmpty()) return input;
        try {
            return input.replaceAll(pattern, replacement);
        } catch (Exception e) {
            return input; // 替换失败时返回原字符串
        }
    }
}
