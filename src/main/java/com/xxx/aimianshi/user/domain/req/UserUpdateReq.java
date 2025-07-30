package com.xxx.aimianshi.user.domain.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdateReq {
    @NotBlank
    private String id;
    private String nickname;
    private String profile;
    private String grade;
    private String workExperience;
    private String expertiseDirection;
}
