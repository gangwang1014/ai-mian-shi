package com.xxx.aimianshi.common.handler;

import com.xxx.aimianshi.common.annotation.ExceptionMapping;
import com.xxx.aimianshi.common.domain.Result;
import com.xxx.aimianshi.common.exception.ApiError;
import com.xxx.aimianshi.common.exception.BizException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

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
        return Result.error(400, "Parameter validation error", errors);
    }

    @ExceptionHandler(BizException.class)
    public Result<ApiError> handleBizException(BizException e, WebRequest request) {
        ExceptionMapping mapping = e.getClass().getAnnotation(ExceptionMapping.class);
        ApiError error = buildApiError(e, request);
        return Result.error(500, e.getMessage(), error);
    }

    // 兜底异常
    @ExceptionHandler(Exception.class)
    public Result<ApiError> handleException(Exception e) {
        return Result.error(500, e.getMessage());
    }

    private ApiError buildApiError(Exception e, WebRequest request) {
        ExceptionMapping mapping = e.getClass().getAnnotation(ExceptionMapping.class);
        return ApiError.builder()
                .code(mapping != null ? mapping.code() : 500)
                .message(e.getMessage())
                .url(((ServletWebRequest) request).getRequest().getRequestURI())
                .method(((ServletWebRequest) request).getRequest().getMethod())
                .build();
    }
}