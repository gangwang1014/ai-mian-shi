package com.xxx.mianshiya.common.exception;

import com.xxx.mianshiya.common.annotation.ExceptionMapping;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@ExceptionMapping(code = 500, status = HttpStatus.BAD_REQUEST)
public class BizException extends RuntimeException {
    private final String message;
    public BizException(String message) {
        super(message);
        this.message = message;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
