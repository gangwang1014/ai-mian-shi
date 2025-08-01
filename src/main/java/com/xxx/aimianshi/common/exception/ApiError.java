package com.xxx.aimianshi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {
    private Integer code;
    private String message;
    private String url;
    private String method;
}
