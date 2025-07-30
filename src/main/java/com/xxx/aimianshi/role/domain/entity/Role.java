package com.xxx.aimianshi.role.domain.entity;

import com.xxx.aimianshi.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {
    private Long id;
    private String name;
}
