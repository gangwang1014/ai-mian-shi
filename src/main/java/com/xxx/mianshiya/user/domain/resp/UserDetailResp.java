package com.xxx.mianshiya.user.domain.resp;

import com.xxx.mianshiya.user.annotation.Sensitive;
import com.xxx.mianshiya.user.enums.SensitiveStrategy;
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
