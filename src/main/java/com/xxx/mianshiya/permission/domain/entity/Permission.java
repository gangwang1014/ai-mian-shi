package com.xxx.mianshiya.permission.domain.entity;

import com.xxx.mianshiya.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Permission extends BaseEntity {
    private Long id;
    private String code;
    private String url;
    private String method;
    private String description;
}
