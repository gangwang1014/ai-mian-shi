package com.xxx.aimianshi.user.domain.req;

import com.xxx.aimianshi.user.annotation.PasswordConfirmed;
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
@PasswordConfirmed(passwordField = "newPassword", confirmField = "confirmNewPassword")
public class ChangePasswordReq {
    @NotBlank
    private Long id;
    @NotBlank
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    @Pattern(regexp = UserConstant.USER_PASSWORD_PATTEN)
    private String newPassword;
    @NotBlank
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    @Pattern(regexp = UserConstant.USER_PASSWORD_PATTEN)
    private String confirmNewPassword;
}
