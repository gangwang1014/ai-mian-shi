package com.xxx.aimianshi.common.exception;

import com.xxx.aimianshi.common.annotation.ExceptionMapping;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
@ExceptionMapping(code = 401, status = HttpStatus.UNAUTHORIZED)
public class TokenAuthenticationException extends AuthenticationException {
    private final String message;
    public TokenAuthenticationException(String message) {
        super(message);
        this.message = message;
    }

    public TokenAuthenticationException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
