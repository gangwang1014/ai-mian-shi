package com.xxx.aimianshi.user.domain.entity;

import com.xxx.aimianshi.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class User extends BaseEntity {

    private Long id;

    private String account;

    private String password;

    private String nickname;

    private String avatar;

    private String email;

    private String phone;

    private String profile;

    private String grade;

    private String workExperience;

    private String expertiseDirection;

    private String unionId;

    private String mpOpenId;
}

