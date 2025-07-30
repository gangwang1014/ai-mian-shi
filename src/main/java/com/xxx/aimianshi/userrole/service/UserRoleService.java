package com.xxx.aimianshi.userrole.service;

import com.xxx.aimianshi.role.enums.RoleEnum;
import com.xxx.aimianshi.userrole.domain.entity.UserRole;

public interface UserRoleService {

    boolean addUserRole(Long userId, RoleEnum roleEnum);

    boolean deleteUserRoles(Long userId);

    void add(UserRole userRole);
}
