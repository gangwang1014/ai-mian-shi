package com.xxx.aimianshi.user.domain.req;

import com.xxx.aimianshi.user.constant.UserConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLoginReq {
    @NotBlank(message = "Account must not be blank")
    @Size(min = 5, message = "Account must be at least 5 characters long")
    private String account;
    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    @Pattern(regexp = UserConstant.USER_PASSWORD_PATTEN)
    private String password;
}
