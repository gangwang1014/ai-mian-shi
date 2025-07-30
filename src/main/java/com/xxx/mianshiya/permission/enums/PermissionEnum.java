package com.xxx.mianshiya.permission.enums;

import lombok.Getter;

@Getter
public enum PermissionEnum {
    ADMIN_ALL("admin:all"),
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_QUERY("admin:query"),

    USER_ALL("user:all"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_DELETE("user:delete"),
    USER_UPDATE("user:update"),
    USER_QUERY("user:query"),
    ;

    private final String code;

    PermissionEnum(String code) {
        this.code = code;
    }
}
