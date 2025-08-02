package com.xxx.aimianshi.user.domain.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserToken {
    private  Long userId;
    private String tokenId;
    private String token;
}
