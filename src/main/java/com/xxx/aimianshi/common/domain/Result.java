package com.xxx.aimianshi.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Result 用于统一返回 json 数据
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T>{
    private Integer code;
    private String msg;
    private T data;

    private static final String SUCCESS = "success";

    public static <T> Result<T> success() {
        return new Result<>(200, SUCCESS, null);
    }
    public static <T> Result<T> success(T data) {
        return new Result<>(200, SUCCESS, data);
    }
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }
    public static <T> Result<T> success(Integer code) {
        return new Result<>(code, SUCCESS, null);
    }

    // error 500
    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }

    public static <T> Result<T> error() {
        return new Result<>(500,"内部服务器错误", null);
    }

    public static <T> Result<T> error(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> of(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

}
