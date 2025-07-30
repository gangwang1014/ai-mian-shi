package com.xxx.aimianshi.user.domain.resp;

import com.xxx.aimianshi.user.annotation.Sensitive;
import com.xxx.aimianshi.user.enums.SensitiveStrategy;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailResp {
    private Long id;
    private String account;
    private String nickname;
    private String avatar;
    @Sensitive(strategy = SensitiveStrategy.EMAIL)
    private String email;
    @Sensitive(strategy = SensitiveStrategy.PHONE)
    private String phone;
    private String profile;
    private String grade;
    private String workExperience;
    private String expertiseDirection;
}
