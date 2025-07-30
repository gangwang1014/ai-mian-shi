package com.xxx.aimianshi.user.domain.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResp {
    private Long id;
    private String account;
    private String nickname;
    private String token;
}
