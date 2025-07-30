package com.xxx.aimianshi.common.exception;

import com.xxx.aimianshi.common.annotation.ExceptionMapping;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@ExceptionMapping(code = 500, status = HttpStatus.BAD_REQUEST)
public class UserAlreadyRegisterException extends BizException {
    private String message;
    public UserAlreadyRegisterException(String message) {
        super(message);
    }
    public UserAlreadyRegisterException(String message, Throwable cause) {
        super(message, cause);
    }
}