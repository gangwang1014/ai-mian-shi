package com.xxx.mianshiya.rolepermission.domain.entity;

import com.xxx.mianshiya.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class RolePermission extends BaseEntity {
    private Long id;
    private Long roleId;
    private Long permissionId;
}
