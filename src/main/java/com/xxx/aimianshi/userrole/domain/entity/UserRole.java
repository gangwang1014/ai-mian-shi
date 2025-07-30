package com.xxx.aimianshi.userrole.domain.entity;

import com.xxx.aimianshi.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class UserRole extends BaseEntity {
    private Long id;
    private Long userId;
    private Long roleId;
}
