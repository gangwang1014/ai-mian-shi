package com.xxx.aimianshi.common.handler;

import com.xxx.aimianshi.common.annotation.ExceptionMapping;
import com.xxx.aimianshi.common.domain.Result;
import com.xxx.aimianshi.common.exception.BizException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );
        return Result.error(500, "Parameter validation error", errors);
    }

    @ExceptionHandler(BizException.class)
    public Result<?> handleBizException(BizException e) {
        ExceptionMapping mapping = e.getClass().getAnnotation(ExceptionMapping.class);
        if (mapping != null) {
            return Result.error(mapping.code(), e.getMessage());
        }
        return Result.error(500, e.getMessage());
    }

    // 兜底异常
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        return Result.error(500, e.getMessage());
    }
}