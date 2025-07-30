package com.xxx.aimianshi.role.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.aimianshi.role.domain.entity.Role;

import java.util.Optional;

public interface RoleRepository extends IService<Role> {
    Optional<Role> findByRoleName(String roleName);
}
