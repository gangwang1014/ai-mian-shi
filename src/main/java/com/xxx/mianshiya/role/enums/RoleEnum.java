package com.xxx.mianshiya.role.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER"),
    BAN("BAN"),
    VIP("VIP"),
    ;

    private final String name;

    RoleEnum(String name) {
        this.name = name;
    }
}
